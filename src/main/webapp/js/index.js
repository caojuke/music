
var audioPlayer = document.querySelector('audio'); //按标签选择对象
var container = document.querySelector('.container');//按class选择对象
var ul = document.querySelector('.container ul');//按class对象选择container对象，再按标签选择子元素ul

 

var lrcArray = lrc.split("\n");
var result = [];
function showText(data){
    console.log('response: '+ JSON.stringify(data));
}
function parseTime(timeStr){
    var parts = timeStr.split(':');
    var time = +parts[0]*60 + +parts[1];//注意此处，字符串前加‘+’号来转换成数字！！！
    return time;
}

function parseLrc(){
    //fragment对象是用来存储dom对象的缓存器，它本身不属于页面的内容
    var frag = document.createDocumentFragment();
    for(i=0;i<lrcArray.length;i++){
        var parts = lrcArray[i].split("]");
        var obj = {
            time: parseTime(parts[0].substring(1)),
            words: parts[1]
        }
        result.push(obj);
        var li = document.createElement('li');
        li.textContent = parts[1];
        frag.appendChild(li);    
    }
    //append words to ul
    ul.appendChild(frag);
}
parseLrc();
console.log(result.length);

/***
 * 获取当前行
 */
function getIndex(){
    var curTime = audioPlayer.currentTime;
    var curLine = 0;
    for(var i=0;i<result.length;i++){
        if(result[i].time>curTime){
            return curLine = i-1;            
        }        
    }
    return result.length-1;
}

var containerHeight = container.clientHeight;
var liHeight = ul.children[0].clientHeight;
var uiHeight = ul.clientHeight; 
/***
 * 根据当前行更新显示,时间调用
 */
function updateDisplay(){
    var curLine = getIndex();    
    if(curLine<0)curLine=0;
    
    var curLi = ul.children[curLine];
    //保持当前行在container中居中显示，因此要移动ul位置，但是直接修改ul的位移会造成布局更新。
    //可以使用transform属性来修改纵坐标的偏移来实现，不会导致布局更新而产生redraw任务！！！
    var offset = containerHeight/2-liHeight/2 - curLine*liHeight;
    if(offset>0)offset=0;//上边界
    if(offset<(containerHeight-uiHeight))offset=containerHeight-uiHeight;//下边界
    console.log(curLine + ':' + offset);
    ul.style.transform = `translateY(-${-offset}px)`;
    //去掉之前行的效果，将目标行效果激活
    var activeLi = document.querySelector('.active');
    if(activeLi)activeLi.classList.remove('active');
    curLi.classList.add('active');
}


audioPlayer.addEventListener('timeupdate', updateDisplay);

// document.getElementById('loadDataBtn').addEventListener('click', function() {
//     // Use Fetch API to make a request
//     fetch('data.txt')
//         .then(response => response.text())
//         .then(data => {
//             // Update the content with the response
//             document.getElementById('content').innerHTML = data;
//         })
//         .catch(error => console.log('Error:', error));
// });
document.getElementById('loadText').addEventListener('click', function() {
    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();
    
    // 这个请求的origin=127.0.0.1:8080，而当前页面的origin=localhost：8080，因此这是个跨域请求！已经在web.xml配置了assets文件夹资源可跨域，其他情形跨域仍旧不可以！
    xhr.open('GET', 'http://127.0.0.1:8080/music/assets/secret.txt', true);
    
    // Define what happens when the response is ready
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Update the content with the response
            document.getElementById('content').innerHTML = '数据内容: '+xhr.responseText;
            setTimeout(goToBranchPage,1000)
        }
    };
    
    // Send the request
    xhr.send();
});

function goToBranchPage(){
    // Create a new XMLHttpRequest object
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:8080/music/JSP/first', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Check if response is an HTML page
            if (xhr.getResponseHeader("Content-Type").indexOf("text/html") !== -1) {
                // Redirect to the returned HTML content
                document.open();
                document.write(xhr.responseText);
                document.close();
            } else {
                console.log("Response is not an HTML page");
            }
        }
    };
    // Send the request
    xhr.send();
}

var type=navigator.appName 
if (type=="Netscape") 
var lang = navigator.language 
else 
var lang = navigator.userLanguage 

//cut down to first 2 chars of country code 
var lang = lang.substr(0,2) 

// 영어
if (lang == "en") 
window.location.replace('../html-en/index.html') 

// 한글
else if (lang == "ko") 
window.location.replace('../html-ko/index.html') 

// 다른언어
else 
window.location.replace('../html-en/index.html')

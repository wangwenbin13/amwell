/**  
* window.onresize 事件 专用事件绑定器 v0.1 Alucelx  
* http://www.cnblogs.com/Alucelx/archive/2011/10/20/2219263.html 
* <description>  
* 用于解决 lte ie8 & chrome 及其他可能会出现的 原生 window.resize 事件多次执行的 BUG.  
* </description>  
* <methods>  
* add: 添加事件句柄  
* remove: 删除事件句柄  
* </methods>  
*/   
var onWindowResize = function(){  
    //事件队列   
    var queue = [],  
        indexOf = Array.prototype.indexOf || function(){  
            var i = 0, length = this.length;   
            for( ; i < length; i++ ){   
                if(this[i] === arguments[0]){  
                    return i;  
                }  
            }  
            return -1;  
        };  
    var isResizing = {}, //标记可视区域尺寸状态， 用于消除 lte ie8 / chrome 中 window.onresize 事件多次执行的 bug   
    lazy = true, //懒执行标记   
    listener = function(e){ //事件监听器   
        var h = window.innerHeight || (document.documentElement && document.documentElement.clientHeight) || document.body.clientHeight,   
        w = window.innerWidth || (document.documentElement && document.documentElement.clientWidth) || document.body.clientWidth;   
        if( h === isResizing.h && w === isResizing.w){   
            return;   
        }else{  
            e = e || window.event;   
            var i = 0, len = queue.length;   
            for( ; i < len; i++){   
                queue[i].call(this, e);   
            }   
            isResizing.h = h,   
            isResizing.w = w;   
        }   
    };   
    return {  
        init: function(){  
            if(lazy){ //懒执行   
                if(window.addEventListener){   
                    window.addEventListener('resize', listener, false);   
                }else{   
                    window.attachEvent('onresize', listener);   
                }   
                lazy = false;   
            }  
        },  
        add: function(fn){  
            if(typeof fn === 'function'){  
                this.init();  
                queue.push(fn);   
            }else{ }   
            return this;   
        },   
        remove: function(fn){  
            if(typeof fn === 'undefined'){   
                queue = [];   
            }else if(typeof fn === 'function'){   
                var i = indexOf.call(queue, fn);   
                if(i > -1){   
                    queue.splice(i, 1);   
                }   
            }   
            return this;   
        },  
        insert: function(index,fn){  
            if(typeof fn === 'function'){  
                this.init();  
                var len=queue.length;  
                if(index>=len){  
                    queue[index]=fn;  
                }else{  
                    for(var i=len-1;i>=index;i--){  
                        queue[i+1]=queue[i];  
                    }  
                    queue[index]=fn;  
                }  
            }else{ }   
            return this;   
        },  
        trigger: function(){  
            var len=queue.length;  
            for(var i=0;i<len;i++){  
                queue[i]();  
            }  
        }  
    };   
}.call(this);  
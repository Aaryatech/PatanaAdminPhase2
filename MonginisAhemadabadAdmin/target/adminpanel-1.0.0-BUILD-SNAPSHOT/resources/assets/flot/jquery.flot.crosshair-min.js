(function(b){var a={crosshair:{mode:null,color:"rgba(170, 0, 0, 0.80)",lineWidth:1}};function c(h){var j={x:-1,y:-1,locked:false};h.setCrosshair=function e(l){if(!l){j.x=-1}else{var k=h.p2c(l);j.x=Math.max(0,Math.min(k.left,h.width()));j.y=Math.max(0,Math.min(k.top,h.height()))}h.triggerRedrawOverlay()};h.clearCrosshair=h.setCrosshair;h.lockCrosshair=function f(k){if(k){h.setCrosshair(k)}j.locked=true};h.unlockCrosshair=function g(){j.locked=false};function d(k){if(j.locked){return}if(j.x!=-1){j.x=-1;h.triggerRedrawOverlay()}}function i(k){if(j.locked){return}if(h.getSelection&&h.getSelection()){j.x=-1;return}var l=h.offset();j.x=Math.max(0,Math.min(k.pageX-l.left,h.width()));j.y=Math.max(0,Math.min(k.pageY-l.top,h.height()));h.triggerRedrawOverlay()}h.hooks.bindEvents.push(function(l,k){if(!l.getOptions().crosshair.mode){return}k.mouseout(d);k.mousemove(i)});h.hooks.drawOverlay.push(function(p,k){var q=p.getOptions().crosshair;if(!q.mode){return}var m=p.getPlotOffset();k.save();k.translate(m.left,m.top);if(j.x!=-1){var l=p.getOptions().crosshair.lineWidth%2===0?0:0.5;k.strokeStyle=q.color;k.lineWidth=q.lineWidth;k.lineJoin="round";k.beginPath();if(q.mode.indexOf("x")!=-1){var o=Math.round(j.x)+l;k.moveTo(o,0);k.lineTo(o,p.height())}if(q.mode.indexOf("y")!=-1){var n=Math.round(j.y)+l;k.moveTo(0,n);k.lineTo(p.width(),n)}k.stroke()}k.restore()});h.hooks.shutdown.push(function(l,k){k.unbind("mouseout",d);k.unbind("mousemove",i)})}b.plot.plugins.push({init:c,options:a,name:"crosshair",version:"1.0"})})(jQuery);
/*! Copyright (c) 2011 Piotr Rochala (http://rocha.la)
 * Dual licensed under the MIT (http://www.opensource.org/licenses/mit-license.php)
 * and GPL (http://www.opensource.org/licenses/gpl-license.php) licenses.
 *
 * Version: 1.3.1
 *
 */
(function(a){jQuery.fn.extend({slimScroll:function(c){var b=a.extend({width:"auto",height:"250px",size:"7px",color:"#000",position:"right",distance:"1px",start:"top",opacity:0.4,alwaysVisible:!1,disableFadeOut:!1,railVisible:!1,railColor:"#333",railOpacity:0.2,railDraggable:!0,railClass:"slimScrollRail",barClass:"slimScrollBar",wrapperClass:"slimScrollDiv",allowPageScroll:!1,wheelStep:20,touchScrollStep:200,borderRadius:"7px",railBorderRadius:"7px"},c);this.each(function(){function G(g){if(o){g=g||window.event;var k=0;g.wheelDelta&&(k=-g.wheelDelta/120);g.detail&&(k=g.detail/3);a(g.target||g.srcTarget||g.srcElement).closest("."+b.wrapperClass).is(R.parent())&&M(k,!0);g.preventDefault&&!O&&g.preventDefault();O||(g.returnValue=!1)}}function M(p,m,k){O=!1;var n=p,l=R.outerHeight()-Q.outerHeight();m&&(n=parseInt(Q.css("top"))+p*parseInt(b.wheelStep)/100*Q.outerHeight(),n=Math.min(Math.max(n,0),l),n=0<p?Math.ceil(n):Math.floor(n),Q.css({top:n+"px"}));N=parseInt(Q.css("top"))/(R.outerHeight()-Q.outerHeight());n=N*(R[0].scrollHeight-R.outerHeight());k&&(n=p,p=n/R[0].scrollHeight*R.outerHeight(),p=Math.min(Math.max(p,0),l),Q.css({top:p+"px"}));R.scrollTop(n);R.trigger("slimscrolling",~~n);i();K()}function F(){window.addEventListener?(this.addEventListener("DOMMouseScroll",G,!1),this.addEventListener("mousewheel",G,!1)):document.attachEvent("onmousewheel",G)}function h(){j=Math.max(R.outerHeight()/R[0].scrollHeight*R.outerHeight(),E);Q.css({height:j+"px"});var g=j==R.outerHeight()?"none":"block";Q.css({display:g})}function i(){h();clearTimeout(J);N==~~N?(O=b.allowPageScroll,I!=N&&R.trigger("slimscroll",0==~~N?"top":"bottom")):O=!1;I=N;j>=R.outerHeight()?O=!0:(Q.stop(!0,!0).fadeIn("fast"),b.railVisible&&P.stop(!0,!0).fadeIn("fast"))}function K(){b.alwaysVisible||(J=setTimeout(function(){b.disableFadeOut&&o||(f||e)||(Q.fadeOut("slow"),P.fadeOut("slow"))},1000))}var o,f,e,J,d,j,N,I,E=30,O=!1,R=a(this);if(R.parent().hasClass(b.wrapperClass)){var L=R.scrollTop(),Q=R.parent().find("."+b.barClass),P=R.parent().find("."+b.railClass);h();if(a.isPlainObject(c)){if("height" in c&&"auto"==c.height){R.parent().css("height","auto");R.css("height","auto");var H=R.parent().parent().height();R.parent().css("height",H);R.css("height",H)}if("scrollTo" in c){L=parseInt(b.scrollTo)}else{if("scrollBy" in c){L+=parseInt(b.scrollBy)}else{if("destroy" in c){Q.remove();P.remove();R.unwrap();return}}}M(L,!1,!0)}}else{b.height="auto"==b.height?R.parent().height():b.height;L=a("<div></div>").addClass(b.wrapperClass).css({position:"relative",overflow:"hidden",width:b.width,height:b.height});R.css({overflow:"hidden",width:b.width,height:b.height});var P=a("<div></div>").addClass(b.railClass).css({width:b.size,height:"100%",position:"absolute",top:0,display:b.alwaysVisible&&b.railVisible?"block":"none","border-radius":b.railBorderRadius,background:b.railColor,opacity:b.railOpacity,zIndex:90}),Q=a("<div></div>").addClass(b.barClass).css({background:b.color,width:b.size,position:"absolute",top:0,opacity:b.opacity,display:b.alwaysVisible?"block":"none","border-radius":b.borderRadius,BorderRadius:b.borderRadius,MozBorderRadius:b.borderRadius,WebkitBorderRadius:b.borderRadius,zIndex:99}),H="right"==b.position?{right:b.distance}:{left:b.distance};P.css(H);Q.css(H);R.wrap(L);R.parent().append(Q);R.parent().append(P);b.railDraggable&&Q.bind("mousedown",function(k){var g=a(document);e=!0;t=parseFloat(Q.css("top"));pageY=k.pageY;g.bind("mousemove.slimscroll",function(l){currTop=t+l.pageY-pageY;Q.css("top",currTop);M(0,Q.position().top,!1)});g.bind("mouseup.slimscroll",function(l){e=!1;K();g.unbind(".slimscroll")});return !1}).bind("selectstart.slimscroll",function(g){g.stopPropagation();g.preventDefault();return !1});P.hover(function(){i()},function(){K()});Q.hover(function(){f=!0},function(){f=!1});R.hover(function(){o=!0;i();K()},function(){o=!1;K()});R.bind("touchstart",function(k,g){k.originalEvent.touches.length&&(d=k.originalEvent.touches[0].pageY)});R.bind("touchmove",function(g){O||g.originalEvent.preventDefault();g.originalEvent.touches.length&&(M((d-g.originalEvent.touches[0].pageY)/b.touchScrollStep,!0),d=g.originalEvent.touches[0].pageY)});h();"bottom"===b.start?(Q.css({top:R.outerHeight()-Q.outerHeight()}),M(0,!0)):"top"!==b.start&&(M(a(b.start).position().top,null,!0),b.alwaysVisible||Q.hide());F()}});return this}});jQuery.fn.extend({slimscroll:jQuery.fn.slimScroll})})(jQuery);
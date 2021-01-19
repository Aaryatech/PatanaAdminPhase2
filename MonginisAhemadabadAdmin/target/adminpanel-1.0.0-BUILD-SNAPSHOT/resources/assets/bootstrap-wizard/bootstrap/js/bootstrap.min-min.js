/*!
* Bootstrap.js by @fat & @mdo
* Copyright 2013 Twitter, Inc.
* http://www.apache.org/licenses/LICENSE-2.0.txt
*/
!function(a){a(function(){a.support.transition=function(){var b=function(){var d=document.createElement("bootstrap"),c={WebkitTransition:"webkitTransitionEnd",MozTransition:"transitionend",OTransition:"oTransitionEnd otransitionend",transition:"transitionend"},f;for(f in c){if(d.style[f]!==undefined){return c[f]}}}();return b&&{end:b}}()})}(window.jQuery),!function(c){var a='[data-dismiss="alert"]',d=function(e){c(e).on("click",a,this.close)};d.prototype.close=function(f){function g(){e.trigger("closed").remove()}var j=c(this),h=j.attr("data-target"),e;h||(h=j.attr("href"),h=h&&h.replace(/.*(?=#[^\s]*$)/,"")),e=c(h),f&&f.preventDefault(),e.length||(e=j.hasClass("alert")?j:j.parent()),e.trigger(f=c.Event("close"));if(f.isDefaultPrevented()){return}e.removeClass("in"),c.support.transition&&e.hasClass("fade")?e.on(c.support.transition.end,g):g()};var b=c.fn.alert;c.fn.alert=function(e){return this.each(function(){var g=c(this),f=g.data("alert");f||g.data("alert",f=new d(this)),typeof e=="string"&&f[e].call(g)})},c.fn.alert.Constructor=d,c.fn.alert.noConflict=function(){return c.fn.alert=b,this},c(document).on("click.alert.data-api",a,d.prototype.close)}(window.jQuery),!function(b){var a=function(d,e){this.$element=b(d),this.options=b.extend({},b.fn.button.defaults,e)};a.prototype.setState=function(h){var f="disabled",j=this.$element,g=j.data(),d=j.is("input")?"val":"html";h+="Text",g.resetText||j.data("resetText",j[d]()),j[d](g[h]||this.options[h]),setTimeout(function(){h=="loadingText"?j.addClass(f).attr(f,f):j.removeClass(f).removeAttr(f)},0)},a.prototype.toggle=function(){var d=this.$element.closest('[data-toggle="buttons-radio"]');d&&d.find(".active").removeClass("active"),this.$element.toggleClass("active")};var c=b.fn.button;b.fn.button=function(d){return this.each(function(){var g=b(this),e=g.data("button"),f=typeof d=="object"&&d;e||g.data("button",e=new a(this,f)),d=="toggle"?e.toggle():d&&e.setState(d)})},b.fn.button.defaults={loadingText:"loading..."},b.fn.button.Constructor=a,b.fn.button.noConflict=function(){return b.fn.button=c,this},b(document).on("click.button.data-api","[data-toggle^=button]",function(d){var e=b(d.target);e.hasClass("btn")||(e=e.closest(".btn")),e.button("toggle")})}(window.jQuery),!function(b){var a=function(d,e){this.$element=b(d),this.$indicators=this.$element.find(".carousel-indicators"),this.options=e,this.options.pause=="hover"&&this.$element.on("mouseenter",b.proxy(this.pause,this)).on("mouseleave",b.proxy(this.cycle,this))};a.prototype={cycle:function(d){return d||(this.paused=!1),this.interval&&clearInterval(this.interval),this.options.interval&&!this.paused&&(this.interval=setInterval(b.proxy(this.next,this),this.options.interval)),this},getActiveIndex:function(){return this.$active=this.$element.find(".item.active"),this.$items=this.$active.parent().children(),this.$items.index(this.$active)},to:function(d){var f=this.getActiveIndex(),e=this;if(d>this.$items.length-1||d<0){return}return this.sliding?this.$element.one("slid",function(){e.to(d)}):f==d?this.pause().cycle():this.slide(d>f?"next":"prev",b(this.$items[d]))},pause:function(d){return d||(this.paused=!0),this.$element.find(".next, .prev").length&&b.support.transition.end&&(this.$element.trigger(b.support.transition.end),this.cycle(!0)),clearInterval(this.interval),this.interval=null,this},next:function(){if(this.sliding){return}return this.slide("next")},prev:function(){if(this.sliding){return}return this.slide("prev")},slide:function(m,g){var d=this.$element.find(".item.active"),h=g||d[m](),p=this.interval,e=m=="next"?"left":"right",l=m=="next"?"first":"last",k=this,j;this.sliding=!0,p&&this.pause(),h=h.length?h:this.$element.find(".item")[l](),j=b.Event("slide",{relatedTarget:h[0],direction:e});if(h.hasClass("active")){return}this.$indicators.length&&(this.$indicators.find(".active").removeClass("active"),this.$element.one("slid",function(){var f=b(k.$indicators.children()[k.getActiveIndex()]);f&&f.addClass("active")}));if(b.support.transition&&this.$element.hasClass("slide")){this.$element.trigger(j);if(j.isDefaultPrevented()){return}h.addClass(m),h[0].offsetWidth,d.addClass(e),h.addClass(e),this.$element.one(b.support.transition.end,function(){h.removeClass([m,e].join(" ")).addClass("active"),d.removeClass(["active",e].join(" ")),k.sliding=!1,setTimeout(function(){k.$element.trigger("slid")},0)})}else{this.$element.trigger(j);if(j.isDefaultPrevented()){return}d.removeClass("active"),h.addClass("active"),this.sliding=!1,this.$element.trigger("slid")}return p&&this.cycle(),this}};var c=b.fn.carousel;b.fn.carousel=function(d){return this.each(function(){var g=b(this),e=g.data("carousel"),f=b.extend({},b.fn.carousel.defaults,typeof d=="object"&&d),h=typeof d=="string"?d:f.slide;e||g.data("carousel",e=new a(this,f)),typeof d=="number"?e.to(d):h?e[h]():f.interval&&e.pause().cycle()})},b.fn.carousel.defaults={interval:5000,pause:"hover"},b.fn.carousel.Constructor=a,b.fn.carousel.noConflict=function(){return b.fn.carousel=c,this},b(document).on("click.carousel.data-api","[data-slide], [data-slide-to]",function(e){var j=b(this),g,d=b(j.attr("data-target")||(g=j.attr("href"))&&g.replace(/.*(?=#[^\s]+$)/,"")),f=b.extend({},d.data(),j.data()),h;d.carousel(f),(h=j.attr("data-slide-to"))&&d.data("carousel").pause().to(h).cycle(),e.preventDefault()})}(window.jQuery),!function(b){var a=function(d,e){this.$element=b(d),this.options=b.extend({},b.fn.collapse.defaults,e),this.options.parent&&(this.$parent=b(this.options.parent)),this.options.toggle&&this.toggle()};a.prototype={constructor:a,dimension:function(){var d=this.$element.hasClass("width");return d?"width":"height"},show:function(){var e,g,f,d;if(this.transitioning||this.$element.hasClass("in")){return}e=this.dimension(),g=b.camelCase(["scroll",e].join("-")),f=this.$parent&&this.$parent.find("> .accordion-group > .in");if(f&&f.length){d=f.data("collapse");if(d&&d.transitioning){return}f.collapse("hide"),d||f.data("collapse",null)}this.$element[e](0),this.transition("addClass",b.Event("show"),"shown"),b.support.transition&&this.$element[e](this.$element[0][g])},hide:function(){var d;if(this.transitioning||!this.$element.hasClass("in")){return}d=this.dimension(),this.reset(this.$element[d]()),this.transition("removeClass",b.Event("hide"),"hidden"),this.$element[d](0)},reset:function(f){var d=this.dimension();return this.$element.removeClass("collapse")[d](f||"auto")[0].offsetWidth,this.$element[f!==null?"addClass":"removeClass"]("collapse"),this},transition:function(e,h,g){var d=this,f=function(){h.type=="show"&&d.reset(),d.transitioning=0,d.$element.trigger(g)};this.$element.trigger(h);if(h.isDefaultPrevented()){return}this.transitioning=1,this.$element[e]("in"),b.support.transition&&this.$element.hasClass("collapse")?this.$element.one(b.support.transition.end,f):f()},toggle:function(){this[this.$element.hasClass("in")?"hide":"show"]()}};var c=b.fn.collapse;b.fn.collapse=function(d){return this.each(function(){var g=b(this),e=g.data("collapse"),f=b.extend({},b.fn.collapse.defaults,g.data(),typeof d=="object"&&d);e||g.data("collapse",e=new a(this,f)),typeof d=="string"&&e[d]()})},b.fn.collapse.defaults={toggle:!0},b.fn.collapse.Constructor=a,b.fn.collapse.noConflict=function(){return b.fn.collapse=c,this},b(document).on("click.collapse.data-api","[data-toggle=collapse]",function(e){var h=b(this),g,d=h.attr("data-target")||e.preventDefault()||(g=h.attr("href"))&&g.replace(/.*(?=#[^\s]+$)/,""),f=b(d).data("collapse")?"toggle":h.data();h[b(d).hasClass("in")?"addClass":"removeClass"]("collapsed"),b(d).collapse(f)})}(window.jQuery),!function(f){function d(){f(".dropdown-backdrop").remove(),f(a).each(function(){b(f(this)).removeClass("open")})}function b(e){var i=e.attr("data-target"),h;i||(i=e.attr("href"),i=i&&/#/.test(i)&&i.replace(/.*(?=#[^\s]*$)/,"")),h=i&&f(i);if(!h||!h.length){h=e.parent()}return h}var a="[data-toggle=dropdown]",g=function(e){var h=f(e).on("click.dropdown.data-api",this.toggle);f("html").on("click.dropdown.data-api",function(){h.parent().removeClass("open")})};g.prototype={constructor:g,toggle:function(e){var j=f(this),h,i;if(j.is(".disabled, :disabled")){return}return h=b(j),i=h.hasClass("open"),d(),i||("ontouchstart" in document.documentElement&&f('<div class="dropdown-backdrop"/>').insertBefore(f(this)).on("click",d),h.toggleClass("open")),j.focus(),!1},keydown:function(m){var j,i,l,h,e,k;if(!/(38|40|27)/.test(m.keyCode)){return}j=f(this),m.preventDefault(),m.stopPropagation();if(j.is(".disabled, :disabled")){return}h=b(j),e=h.hasClass("open");if(!e||e&&m.keyCode==27){return m.which==27&&h.find(a).focus(),j.click()}i=f("[role=menu] li:not(.divider):visible a",h);if(!i.length){return}k=i.index(i.filter(":focus")),m.keyCode==38&&k>0&&k--,m.keyCode==40&&k<i.length-1&&k++,~k||(k=0),i.eq(k).focus()}};var c=f.fn.dropdown;f.fn.dropdown=function(e){return this.each(function(){var j=f(this),h=j.data("dropdown");h||j.data("dropdown",h=new g(this)),typeof e=="string"&&h[e].call(j)})},f.fn.dropdown.Constructor=g,f.fn.dropdown.noConflict=function(){return f.fn.dropdown=c,this},f(document).on("click.dropdown.data-api",d).on("click.dropdown.data-api",".dropdown form",function(h){h.stopPropagation()}).on("click.dropdown.data-api",a,g.prototype.toggle).on("keydown.dropdown.data-api",a+", [role=menu]",g.prototype.keydown)}(window.jQuery),!function(b){var a=function(d,e){this.options=e,this.$element=b(d).delegate('[data-dismiss="modal"]',"click.dismiss.modal",b.proxy(this.hide,this)),this.options.remote&&this.$element.find(".modal-body").load(this.options.remote)};a.prototype={constructor:a,toggle:function(){return this[this.isShown?"hide":"show"]()},show:function(){var d=this,e=b.Event("show");this.$element.trigger(e);if(this.isShown||e.isDefaultPrevented()){return}this.isShown=!0,this.escape(),this.backdrop(function(){var f=b.support.transition&&d.$element.hasClass("fade");d.$element.parent().length||d.$element.appendTo(document.body),d.$element.show(),f&&d.$element[0].offsetWidth,d.$element.addClass("in").attr("aria-hidden",!1),d.enforceFocus(),f?d.$element.one(b.support.transition.end,function(){d.$element.focus().trigger("shown")}):d.$element.focus().trigger("shown")})},hide:function(d){d&&d.preventDefault();var e=this;d=b.Event("hide"),this.$element.trigger(d);if(!this.isShown||d.isDefaultPrevented()){return}this.isShown=!1,this.escape(),b(document).off("focusin.modal"),this.$element.removeClass("in").attr("aria-hidden",!0),b.support.transition&&this.$element.hasClass("fade")?this.hideWithTransition():this.hideModal()},enforceFocus:function(){var d=this;b(document).on("focusin.modal",function(f){d.$element[0]!==f.target&&!d.$element.has(f.target).length&&d.$element.focus()})},escape:function(){var d=this;this.isShown&&this.options.keyboard?this.$element.on("keyup.dismiss.modal",function(e){e.which==27&&d.hide()}):this.isShown||this.$element.off("keyup.dismiss.modal")},hideWithTransition:function(){var d=this,e=setTimeout(function(){d.$element.off(b.support.transition.end),d.hideModal()},500);this.$element.one(b.support.transition.end,function(){clearTimeout(e),d.hideModal()})},hideModal:function(){var d=this;this.$element.hide(),this.backdrop(function(){d.removeBackdrop(),d.$element.trigger("hidden")})},removeBackdrop:function(){this.$backdrop&&this.$backdrop.remove(),this.$backdrop=null},backdrop:function(e){var g=this,f=this.$element.hasClass("fade")?"fade":"";if(this.isShown&&this.options.backdrop){var d=b.support.transition&&f;this.$backdrop=b('<div class="modal-backdrop '+f+'" />').appendTo(document.body),this.$backdrop.click(this.options.backdrop=="static"?b.proxy(this.$element[0].focus,this.$element[0]):b.proxy(this.hide,this)),d&&this.$backdrop[0].offsetWidth,this.$backdrop.addClass("in");if(!e){return}d?this.$backdrop.one(b.support.transition.end,e):e()}else{!this.isShown&&this.$backdrop?(this.$backdrop.removeClass("in"),b.support.transition&&this.$element.hasClass("fade")?this.$backdrop.one(b.support.transition.end,e):e()):e&&e()}}};var c=b.fn.modal;b.fn.modal=function(d){return this.each(function(){var g=b(this),e=g.data("modal"),f=b.extend({},b.fn.modal.defaults,g.data(),typeof d=="object"&&d);e||g.data("modal",e=new a(this,f)),typeof d=="string"?e[d]():f.show&&e.show()})},b.fn.modal.defaults={backdrop:!0,keyboard:!0,show:!0},b.fn.modal.Constructor=a,b.fn.modal.noConflict=function(){return b.fn.modal=c,this},b(document).on("click.modal.data-api",'[data-toggle="modal"]',function(e){var h=b(this),g=h.attr("href"),d=b(h.attr("data-target")||g&&g.replace(/.*(?=#[^\s]+$)/,"")),f=d.data("modal")?"toggle":b.extend({remote:!/#/.test(g)&&g},d.data(),h.data());e.preventDefault(),d.modal(f).one("hide",function(){h.focus()})})}(window.jQuery),!function(b){var a=function(f,d){this.init("tooltip",f,d)};a.prototype={constructor:a,init:function(g,l,j){var f,h,k,e,d;this.type=g,this.$element=b(l),this.options=this.getOptions(j),this.enabled=!0,k=this.options.trigger.split(" ");for(d=k.length;d--;){e=k[d],e=="click"?this.$element.on("click."+this.type,this.options.selector,b.proxy(this.toggle,this)):e!="manual"&&(f=e=="hover"?"mouseenter":"focus",h=e=="hover"?"mouseleave":"blur",this.$element.on(f+"."+this.type,this.options.selector,b.proxy(this.enter,this)),this.$element.on(h+"."+this.type,this.options.selector,b.proxy(this.leave,this)))}this.options.selector?this._options=b.extend({},this.options,{trigger:"manual",selector:""}):this.fixTitle()},getOptions:function(d){return d=b.extend({},b.fn[this.type].defaults,this.$element.data(),d),d.delay&&typeof d.delay=="number"&&(d.delay={show:d.delay,hide:d.delay}),d},enter:function(e){var g=b.fn[this.type].defaults,f={},d;this._options&&b.each(this._options,function(i,h){g[i]!=h&&(f[i]=h)},this),d=b(e.currentTarget)[this.type](f).data(this.type);if(!d.options.delay||!d.options.delay.show){return d.show()}clearTimeout(this.timeout),d.hoverState="in",this.timeout=setTimeout(function(){d.hoverState=="in"&&d.show()},d.options.delay.show)},leave:function(d){var e=b(d.currentTarget)[this.type](this._options).data(this.type);this.timeout&&clearTimeout(this.timeout);if(!e.options.delay||!e.options.delay.hide){return e.hide()}e.hoverState="out",this.timeout=setTimeout(function(){e.hoverState=="out"&&e.hide()},e.options.delay.hide)},show:function(){var f,k,h,e,g,j,d=b.Event("show");if(this.hasContent()&&this.enabled){this.$element.trigger(d);if(d.isDefaultPrevented()){return}f=this.tip(),this.setContent(),this.options.animation&&f.addClass("fade"),g=typeof this.options.placement=="function"?this.options.placement.call(this,f[0],this.$element[0]):this.options.placement,f.detach().css({top:0,left:0,display:"block"}),this.options.container?f.appendTo(this.options.container):f.insertAfter(this.$element),k=this.getPosition(),h=f[0].offsetWidth,e=f[0].offsetHeight;switch(g){case"bottom":j={top:k.top+k.height,left:k.left+k.width/2-h/2};break;case"top":j={top:k.top-e,left:k.left+k.width/2-h/2};break;case"left":j={top:k.top+k.height/2-e/2,left:k.left-h};break;case"right":j={top:k.top+k.height/2-e/2,left:k.left+k.width}}this.applyPlacement(j,g),this.$element.trigger("shown")}},applyPlacement:function(j,m){var g=this.tip(),d=g[0].offsetWidth,h=g[0].offsetHeight,p,f,l,k;g.offset(j).addClass(m).addClass("in"),p=g[0].offsetWidth,f=g[0].offsetHeight,m=="top"&&f!=h&&(j.top=j.top+h-f,k=!0),m=="bottom"||m=="top"?(l=0,j.left<0&&(l=j.left*-2,j.left=0,g.offset(j),p=g[0].offsetWidth,f=g[0].offsetHeight),this.replaceArrow(l-d+p,p,"left")):this.replaceArrow(f-h,f,"top"),k&&g.offset(j)},replaceArrow:function(f,d,g){this.arrow().css(g,f?50*(1-f/d)+"%":"")},setContent:function(){var f=this.tip(),d=this.getTitle();f.find(".tooltip-inner")[this.options.html?"html":"text"](d),f.removeClass("fade in top bottom left right")},hide:function(){function e(){var h=setTimeout(function(){g.off(b.support.transition.end).detach()},500);g.one(b.support.transition.end,function(){clearTimeout(h),g.detach()})}var d=this,g=this.tip(),f=b.Event("hide");this.$element.trigger(f);if(f.isDefaultPrevented()){return}return g.removeClass("in"),b.support.transition&&this.$tip.hasClass("fade")?e():g.detach(),this.$element.trigger("hidden"),this},fixTitle:function(){var d=this.$element;(d.attr("title")||typeof d.attr("data-original-title")!="string")&&d.attr("data-original-title",d.attr("title")||"").attr("title","")},hasContent:function(){return this.getTitle()},getPosition:function(){var d=this.$element[0];return b.extend({},typeof d.getBoundingClientRect=="function"?d.getBoundingClientRect():{width:d.offsetWidth,height:d.offsetHeight},this.$element.offset())},getTitle:function(){var f,d=this.$element,g=this.options;return f=d.attr("data-original-title")||(typeof g.title=="function"?g.title.call(d[0]):g.title),f},tip:function(){return this.$tip=this.$tip||b(this.options.template)},arrow:function(){return this.$arrow=this.$arrow||this.tip().find(".tooltip-arrow")},validate:function(){this.$element[0].parentNode||(this.hide(),this.$element=null,this.options=null)},enable:function(){this.enabled=!0},disable:function(){this.enabled=!1},toggleEnabled:function(){this.enabled=!this.enabled},toggle:function(d){var e=d?b(d.currentTarget)[this.type](this._options).data(this.type):this;e.tip().hasClass("in")?e.hide():e.show()},destroy:function(){this.hide().$element.off("."+this.type).removeData(this.type)}};var c=b.fn.tooltip;b.fn.tooltip=function(d){return this.each(function(){var g=b(this),e=g.data("tooltip"),f=typeof d=="object"&&d;e||g.data("tooltip",e=new a(this,f)),typeof d=="string"&&e[d]()})},b.fn.tooltip.Constructor=a,b.fn.tooltip.defaults={animation:!0,placement:"top",selector:!1,template:'<div class="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',trigger:"hover focus",title:"",delay:0,html:!1,container:!1},b.fn.tooltip.noConflict=function(){return b.fn.tooltip=c,this}}(window.jQuery),!function(b){var a=function(f,d){this.init("popover",f,d)};a.prototype=b.extend({},b.fn.tooltip.Constructor.prototype,{constructor:a,setContent:function(){var f=this.tip(),d=this.getTitle(),g=this.getContent();f.find(".popover-title")[this.options.html?"html":"text"](d),f.find(".popover-content")[this.options.html?"html":"text"](g),f.removeClass("fade top bottom left right in")},hasContent:function(){return this.getTitle()||this.getContent()},getContent:function(){var f,d=this.$element,g=this.options;return f=(typeof g.content=="function"?g.content.call(d[0]):g.content)||d.attr("data-content"),f},tip:function(){return this.$tip||(this.$tip=b(this.options.template)),this.$tip},destroy:function(){this.hide().$element.off("."+this.type).removeData(this.type)}});var c=b.fn.popover;b.fn.popover=function(d){return this.each(function(){var g=b(this),e=g.data("popover"),f=typeof d=="object"&&d;e||g.data("popover",e=new a(this,f)),typeof d=="string"&&e[d]()})},b.fn.popover.Constructor=a,b.fn.popover.defaults=b.extend({},b.fn.tooltip.defaults,{placement:"right",trigger:"click",content:"",template:'<div class="popover"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'}),b.fn.popover.noConflict=function(){return b.fn.popover=c,this}}(window.jQuery),!function(b){function a(e,h){var g=b.proxy(this.process,this),d=b(e).is("body")?b(window):b(e),f;this.options=b.extend({},b.fn.scrollspy.defaults,h),this.$scrollElement=d.on("scroll.scroll-spy.data-api",g),this.selector=(this.options.target||(f=b(e).attr("href"))&&f.replace(/.*(?=#[^\s]+$)/,"")||"")+" .nav li > a",this.$body=b("body"),this.refresh(),this.process()}a.prototype={constructor:a,refresh:function(){var d=this,e;this.offsets=b([]),this.targets=b([]),e=this.$body.find(this.selector).map(function(){var h=b(this),g=h.data("target")||h.attr("href"),f=/^#\w/.test(g)&&b(g);return f&&f.length&&[[f.position().top+(!b.isWindow(d.$scrollElement.get(0))&&d.$scrollElement.scrollTop()),g]]||null}).sort(function(g,f){return g[0]-f[0]}).each(function(){d.offsets.push(this[0]),d.targets.push(this[1])})},process:function(){var j=this.$scrollElement.scrollTop()+this.options.offset,f=this.$scrollElement[0].scrollHeight||this.$body[0].scrollHeight,l=f-this.$scrollElement.height(),h=this.offsets,d=this.targets,g=this.activeTarget,k;if(j>=l){return g!=(k=d.last()[0])&&this.activate(k)}for(k=h.length;k--;){g!=d[k]&&j>=h[k]&&(!h[k+1]||j<=h[k+1])&&this.activate(d[k])}},activate:function(d){var f,e;this.activeTarget=d,b(this.selector).parent(".active").removeClass("active"),e=this.selector+'[data-target="'+d+'"],'+this.selector+'[href="'+d+'"]',f=b(e).parent("li").addClass("active"),f.parent(".dropdown-menu").length&&(f=f.closest("li.dropdown").addClass("active")),f.trigger("activate")}};var c=b.fn.scrollspy;b.fn.scrollspy=function(d){return this.each(function(){var g=b(this),e=g.data("scrollspy"),f=typeof d=="object"&&d;e||g.data("scrollspy",e=new a(this,f)),typeof d=="string"&&e[d]()})},b.fn.scrollspy.Constructor=a,b.fn.scrollspy.defaults={offset:10},b.fn.scrollspy.noConflict=function(){return b.fn.scrollspy=c,this},b(window).on("load",function(){b('[data-spy="scroll"]').each(function(){var d=b(this);d.scrollspy(d.data())})})}(window.jQuery),!function(b){var a=function(d){this.element=b(d)};a.prototype={constructor:a,show:function(){var e=this.element,j=e.closest("ul:not(.dropdown-menu)"),g=e.attr("data-target"),d,f,h;g||(g=e.attr("href"),g=g&&g.replace(/.*(?=#[^\s]*$)/,""));if(e.parent("li").hasClass("active")){return}d=j.find(".active:last a")[0],h=b.Event("show",{relatedTarget:d}),e.trigger(h);if(h.isDefaultPrevented()){return}f=b(g),this.activate(e.parent("li"),j),this.activate(f,f.parent(),function(){e.trigger({type:"shown",relatedTarget:d})})},activate:function(e,j,g){function h(){d.removeClass("active").find("> .dropdown-menu > .active").removeClass("active"),e.addClass("active"),f?(e[0].offsetWidth,e.addClass("in")):e.removeClass("fade"),e.parent(".dropdown-menu")&&e.closest("li.dropdown").addClass("active"),g&&g()}var d=j.find("> .active"),f=g&&b.support.transition&&d.hasClass("fade");f?d.one(b.support.transition.end,h):h(),d.removeClass("in")}};var c=b.fn.tab;b.fn.tab=function(d){return this.each(function(){var f=b(this),e=f.data("tab");e||f.data("tab",e=new a(this)),typeof d=="string"&&e[d]()})},b.fn.tab.Constructor=a,b.fn.tab.noConflict=function(){return b.fn.tab=c,this},b(document).on("click.tab.data-api",'[data-toggle="tab"], [data-toggle="pill"]',function(d){d.preventDefault(),b(this).tab("show")})}(window.jQuery),!function(b){var a=function(d,e){this.$element=b(d),this.options=b.extend({},b.fn.typeahead.defaults,e),this.matcher=this.options.matcher||this.matcher,this.sorter=this.options.sorter||this.sorter,this.highlighter=this.options.highlighter||this.highlighter,this.updater=this.options.updater||this.updater,this.source=this.options.source,this.$menu=b(this.options.menu),this.shown=!1,this.listen()};a.prototype={constructor:a,select:function(){var d=this.$menu.find(".active").attr("data-value");return this.$element.val(this.updater(d)).change(),this.hide()},updater:function(d){return d},show:function(){var d=b.extend({},this.$element.position(),{height:this.$element[0].offsetHeight});return this.$menu.insertAfter(this.$element).css({top:d.top+d.height,left:d.left}).show(),this.shown=!0,this},hide:function(){return this.$menu.hide(),this.shown=!1,this},lookup:function(d){var e;return this.query=this.$element.val(),!this.query||this.query.length<this.options.minLength?this.shown?this.hide():this:(e=b.isFunction(this.source)?this.source(this.query,b.proxy(this.process,this)):this.source,e?this.process(e):this)},process:function(d){var e=this;return d=b.grep(d,function(f){return e.matcher(f)}),d=this.sorter(d),d.length?this.render(d.slice(0,this.options.items)).show():this.shown?this.hide():this},matcher:function(d){return ~d.toLowerCase().indexOf(this.query.toLowerCase())},sorter:function(h){var f=[],j=[],g=[],d;while(d=h.shift()){d.toLowerCase().indexOf(this.query.toLowerCase())?~d.indexOf(this.query)?j.push(d):g.push(d):f.push(d)}return f.concat(j,g)},highlighter:function(f){var d=this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g,"\\$&");return f.replace(new RegExp("("+d+")","ig"),function(h,g){return"<strong>"+g+"</strong>"})},render:function(d){var e=this;return d=b(d).map(function(f,g){return f=b(e.options.item).attr("data-value",g),f.find("a").html(e.highlighter(g)),f[0]}),d.first().addClass("active"),this.$menu.html(d),this},next:function(d){var f=this.$menu.find(".active").removeClass("active"),e=f.next();e.length||(e=b(this.$menu.find("li")[0])),e.addClass("active")},prev:function(f){var d=this.$menu.find(".active").removeClass("active"),g=d.prev();g.length||(g=this.$menu.find("li").last()),g.addClass("active")},listen:function(){this.$element.on("focus",b.proxy(this.focus,this)).on("blur",b.proxy(this.blur,this)).on("keypress",b.proxy(this.keypress,this)).on("keyup",b.proxy(this.keyup,this)),this.eventSupported("keydown")&&this.$element.on("keydown",b.proxy(this.keydown,this)),this.$menu.on("click",b.proxy(this.click,this)).on("mouseenter","li",b.proxy(this.mouseenter,this)).on("mouseleave","li",b.proxy(this.mouseleave,this))},eventSupported:function(f){var d=f in this.$element;return d||(this.$element.setAttribute(f,"return;"),d=typeof this.$element[f]=="function"),d},move:function(d){if(!this.shown){return}switch(d.keyCode){case 9:case 13:case 27:d.preventDefault();break;case 38:d.preventDefault(),this.prev();break;case 40:d.preventDefault(),this.next()}d.stopPropagation()},keydown:function(d){this.suppressKeyPressRepeat=~b.inArray(d.keyCode,[40,38,9,13,27]),this.move(d)},keypress:function(d){if(this.suppressKeyPressRepeat){return}this.move(d)},keyup:function(d){switch(d.keyCode){case 40:case 38:case 16:case 17:case 18:break;case 9:case 13:if(!this.shown){return}this.select();break;case 27:if(!this.shown){return}this.hide();break;default:this.lookup()}d.stopPropagation(),d.preventDefault()},focus:function(d){this.focused=!0},blur:function(d){this.focused=!1,!this.mousedover&&this.shown&&this.hide()},click:function(d){d.stopPropagation(),d.preventDefault(),this.select(),this.$element.focus()},mouseenter:function(d){this.mousedover=!0,this.$menu.find(".active").removeClass("active"),b(d.currentTarget).addClass("active")},mouseleave:function(d){this.mousedover=!1,!this.focused&&this.shown&&this.hide()}};var c=b.fn.typeahead;b.fn.typeahead=function(d){return this.each(function(){var g=b(this),e=g.data("typeahead"),f=typeof d=="object"&&d;e||g.data("typeahead",e=new a(this,f)),typeof d=="string"&&e[d]()})},b.fn.typeahead.defaults={source:[],items:8,menu:'<ul class="typeahead dropdown-menu"></ul>',item:'<li><a href="#"></a></li>',minLength:1},b.fn.typeahead.Constructor=a,b.fn.typeahead.noConflict=function(){return b.fn.typeahead=c,this},b(document).on("focus.typeahead.data-api",'[data-provide="typeahead"]',function(d){var e=b(this);if(e.data("typeahead")){return}e.typeahead(e.data())})}(window.jQuery),!function(b){var a=function(d,e){this.options=b.extend({},b.fn.affix.defaults,e),this.$window=b(window).on("scroll.affix.data-api",b.proxy(this.checkPosition,this)).on("click.affix.data-api",b.proxy(function(){setTimeout(b.proxy(this.checkPosition,this),1)},this)),this.$element=b(d),this.checkPosition()};a.prototype.checkPosition=function(){if(!this.$element.is(":visible")){return}var g=b(document).height(),l=this.$window.scrollTop(),j=this.$element.offset(),f=this.options.offset,h=f.bottom,k=f.top,e="affix affix-top affix-bottom",d;typeof f!="object"&&(h=k=f),typeof k=="function"&&(k=f.top()),typeof h=="function"&&(h=f.bottom()),d=this.unpin!=null&&l+this.unpin<=j.top?!1:h!=null&&j.top+this.$element.height()>=g-h?"bottom":k!=null&&l<=k?"top":!1;if(this.affixed===d){return}this.affixed=d,this.unpin=d=="bottom"?j.top-l:null,this.$element.removeClass(e).addClass("affix"+(d?"-"+d:""))};var c=b.fn.affix;b.fn.affix=function(d){return this.each(function(){var g=b(this),e=g.data("affix"),f=typeof d=="object"&&d;e||g.data("affix",e=new a(this,f)),typeof d=="string"&&e[d]()})},b.fn.affix.Constructor=a,b.fn.affix.defaults={offset:0},b.fn.affix.noConflict=function(){return b.fn.affix=c,this},b(window).on("load",function(){b('[data-spy="affix"]').each(function(){var d=b(this),e=d.data();e.offset=e.offset||{},e.offsetBottom&&(e.offset.bottom=e.offsetBottom),e.offsetTop&&(e.offset.top=e.offsetTop),d.affix(e)})})}(window.jQuery);
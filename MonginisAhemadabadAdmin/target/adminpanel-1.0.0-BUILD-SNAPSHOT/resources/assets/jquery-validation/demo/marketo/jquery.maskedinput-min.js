(function(e){function g(){var i=document.createElement("input"),h="onpaste";i.setAttribute(h,"");return(typeof i[h]==="function")?"paste":"input"}var b=g()+".mask",d=navigator.userAgent,c=/iphone/i.test(d),a=/android/i.test(d),f;e.mask={definitions:{"9":"[0-9]",a:"[A-Za-z]","*":"[A-Za-z0-9]"},dataName:"rawMaskFn",placeholder:"_",};e.fn.extend({caret:function(j,h){var i;if(this.length===0||this.is(":hidden")){return}if(typeof j=="number"){h=(typeof h==="number")?h:j;return this.each(function(){if(this.setSelectionRange){this.setSelectionRange(j,h)}else{if(this.createTextRange){i=this.createTextRange();i.collapse(true);i.moveEnd("character",h);i.moveStart("character",j);i.select()}}})}else{if(this[0].setSelectionRange){j=this[0].selectionStart;h=this[0].selectionEnd}else{if(document.selection&&document.selection.createRange){i=document.selection.createRange();j=0-i.duplicate().moveStart("character",-100000);h=j+i.text.length}}return{begin:j,end:h}}},unmask:function(){return this.trigger("unmask")},mask:function(j,n){var k,i,m,o,l,h;if(!j&&this.length>0){k=e(this[0]);return k.data(e.mask.dataName)()}n=e.extend({placeholder:e.mask.placeholder,completed:null},n);i=e.mask.definitions;m=[];o=h=j.length;l=null;e.each(j.split(""),function(p,q){if(q=="?"){h--;o=p}else{if(i[q]){m.push(new RegExp(i[q]));if(l===null){l=m.length-1}}else{m.push(null)}}});return this.trigger("unmask").each(function(){var y=e(this),t=e.map(j.split(""),function(C,B){if(C!="?"){return i[C]?n.placeholder:C}}),A=y.val();function x(B){while(++B<h&&!m[B]){}return B}function u(B){while(--B>=0&&!m[B]){}return B}function s(E,B){var D,C;if(E<0){return}for(D=E,C=x(B);D<h;D++){if(m[D]){if(C<h&&m[D].test(t[C])){t[D]=t[C];t[C]=n.placeholder}else{break}C=x(C)}}w();y.caret(Math.max(l,E))}function p(F){var D,E,B,C;for(D=F,E=n.placeholder;D<h;D++){if(m[D]){B=x(D);C=t[D];t[D]=E;if(B<h&&m[B].test(C)){E=C}else{break}}}}function v(E){var C=E.which,F,D,B;if(C===8||C===46||(c&&C===127)){F=y.caret();D=F.begin;B=F.end;if(B-D===0){D=C!==46?u(D):(B=x(D-1));B=C===46?x(B):B}q(D,B);s(D,B-1);E.preventDefault()}else{if(C==27){y.val(A);y.caret(0,r());E.preventDefault()}}}function z(E){var B=E.which,G=y.caret(),D,F,C;if(E.ctrlKey||E.altKey||E.metaKey||B<32){return}else{if(B){if(G.end-G.begin!==0){q(G.begin,G.end);s(G.begin,G.end-1)}D=x(G.begin-1);if(D<h){F=String.fromCharCode(B);if(m[D].test(F)){p(D);t[D]=F;w();C=x(D);if(a){setTimeout(e.proxy(e.fn.caret,y,C),0)}else{y.caret(C)}if(n.completed&&C>=h){n.completed.call(y)}}}E.preventDefault()}}}function q(D,B){var C;for(C=D;C<B&&C<h;C++){if(m[C]){t[C]=n.placeholder}}}function w(){y.val(t.join(""))}function r(C){var F=y.val(),E=-1,B,D;for(B=0,pos=0;B<h;B++){if(m[B]){t[B]=n.placeholder;while(pos++<F.length){D=F.charAt(pos-1);if(m[B].test(D)){t[B]=D;E=B;break}}if(pos>F.length){break}}else{if(t[B]===F.charAt(pos)&&B!==o){pos++;E=B}}}if(C){w()}else{if(E+1<o){y.val("");q(0,h)}else{w();y.val(y.val().substring(0,E+1))}}return(o?B:l)}y.data(e.mask.dataName,function(){return e.map(t,function(C,B){return m[B]&&C!=n.placeholder?C:null}).join("")});if(!y.attr("readonly")){y.one("unmask",function(){y.unbind(".mask").removeData(e.mask.dataName)}).bind("focus.mask",function(){clearTimeout(f);var C,B;A=y.val();C=r();f=setTimeout(function(){w();if(C==j.length){y.caret(0,C)}else{y.caret(C)}},10)}).bind("blur.mask",function(){r();if(y.val()!=A){y.change()}}).bind("keydown.mask",v).bind("keypress.mask",z).bind(b,function(){setTimeout(function(){var B=r(true);y.caret(B);if(n.completed&&B==y.val().length){n.completed.call(y)}},0)})}r()})}})})(jQuery);
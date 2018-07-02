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
 * combobox - jQuery EasyUI
 * 
 * Dependencies:
 *   combo
 * 
 */
(function($){
	var COMBOBOX_SERNO = 0;
	
	function getRowIndex(target, value){
		var state = $.data(target, 'combobox');
		var opts = state.options;
		var data = state.data;
		for(var i=0; i<data.length; i++){
			if (data[i][opts.valueField] == value){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * scroll panel to display the specified book
	 */
	function scrollTo(target, value){
		var opts = $.data(target, 'combobox').options;
		var panel = $(target).combo('panel');
		var book = opts.finder.getEl(target, value);
		if (book.length){
			if (book.position().top <= 0){
				var h = panel.scrollTop() + book.position().top;
				panel.scrollTop(h);
			} else if (book.position().top + book.outerHeight() > panel.height()){
				var h = panel.scrollTop() + book.position().top + book.outerHeight() - panel.height();
				panel.scrollTop(h);
			}
		}
	}
	
	function nav(target, dir){
		var opts = $.data(target, 'combobox').options;
		var panel = $(target).combobox('panel');
		var book = panel.children('div.combobox-book-hover');
		if (!book.length){
			book = panel.children('div.combobox-book-selected');
		}
		book.removeClass('combobox-book-hover');
		var firstSelector = 'div.combobox-book:visible:not(.combobox-book-disabled):first';
		var lastSelector = 'div.combobox-book:visible:not(.combobox-book-disabled):last';
		if (!book.length){
			book = panel.children(dir=='next' ? firstSelector : lastSelector);
//			book = panel.children('div.combobox-book:visible:' + (dir=='next'?'first':'last'));
		} else {
			if (dir == 'next'){
				book = book.nextAll(firstSelector);
//				book = book.nextAll('div.combobox-book:visible:first');
				if (!book.length){
					book = panel.children(firstSelector);
//					book = panel.children('div.combobox-book:visible:first');
				}
			} else {
				book = book.prevAll(firstSelector);
//				book = book.prevAll('div.combobox-book:visible:first');
				if (!book.length){
					book = panel.children(lastSelector);
//					book = panel.children('div.combobox-book:visible:last');
				}
			}
		}
		if (book.length){
			book.addClass('combobox-book-hover');
			var row = opts.finder.getRow(target, book);
			if (row){
				scrollTo(target, row[opts.valueField]);
				if (opts.selectOnNavigation){
					select(target, row[opts.valueField]);
				}
			}
		}
	}
	
	/**
	 * select the specified value
	 */
	function select(target, value){
		var opts = $.data(target, 'combobox').options;
		var values = $(target).combo('getValues');
		if ($.inArray(value+'', values) == -1){
			if (opts.multiple){
				values.push(value);
			} else {
				values = [value];
			}
			setValues(target, values);
			opts.onSelect.call(target, opts.finder.getRow(target, value));
		}
	}
	
	/**
	 * unselect the specified value
	 */
	function unselect(target, value){
		var opts = $.data(target, 'combobox').options;
		var values = $(target).combo('getValues');
		var index = $.inArray(value+'', values);
		if (index >= 0){
			values.splice(index, 1);
			setValues(target, values);
			opts.onUnselect.call(target, opts.finder.getRow(target, value));
		}
	}
	
	/**
	 * set values
	 */
	function setValues(target, values, remainText){
		var opts = $.data(target, 'combobox').options;
		var panel = $(target).combo('panel');
		
		if (!$.isArray(values)){values = values.split(opts.separator)}
		panel.find('div.combobox-book-selected').removeClass('combobox-book-selected');
		var vv = [], ss = [];
		for(var i=0; i<values.length; i++){
			var v = values[i];
			var s = v;
			opts.finder.getEl(target, v).addClass('combobox-book-selected');
			var row = opts.finder.getRow(target, v);
			if (row){
				s = row[opts.textField];
			}
			vv.push(v);
			ss.push(s);
		}
		
		$(target).combo('setValues', vv);
		if (!remainText){
			$(target).combo('setText', ss.join(opts.separator));
		}
	}
	
	/**
	 * load data, the old list books will be removed.
	 */
	function loadData(target, data, remainText){
		var state = $.data(target, 'combobox');
		var opts = state.options;
		state.data = opts.loadFilter.call(target, data);
		state.groups = [];
		data = state.data;
		
		var selected = $(target).combobox('getValues');
		var dd = [];
		var group = undefined;
		for(var i=0; i<data.length; i++){
			var row = data[i];
			var v = row[opts.valueField]+'';
			var s = row[opts.textField];
			var g = row[opts.groupField];
			
			if (g){
				if (group != g){
					group = g;
					state.groups.push(g);
					dd.push('<div id="' + (state.groupIdPrefix+'_'+(state.groups.length-1)) + '" class="combobox-group">');
					dd.push(opts.groupFormatter ? opts.groupFormatter.call(target, g) : g);
					dd.push('</div>');
				}
			} else {
				group = undefined;
			}
			
			var cls = 'combobox-book' + (row.disabled ? ' combobox-book-disabled' : '') + (g ? ' combobox-gbook' : '');
			dd.push('<div id="' + (state.bookIdPrefix+'_'+i) + '" class="' + cls + '">');
			dd.push(opts.formatter ? opts.formatter.call(target, row) : s);
			dd.push('</div>');
			
//			if (book['selected']){
//				(function(){
//					for(var i=0; i<selected.length; i++){
//						if (v == selected[i]) return;
//					}
//					selected.push(v);
//				})();
//			}
			if (row['selected'] && $.inArray(v, selected) == -1){
				selected.push(v);
			}
		}
		$(target).combo('panel').html(dd.join(''));
		
		if (opts.multiple){
			setValues(target, selected, remainText);
		} else {
			setValues(target, selected.length ? [selected[selected.length-1]] : [], remainText);
		}
		
		opts.onLoadSuccess.call(target, data);
	}
	
	/**
	 * request remote data if the url property is setted.
	 */
	function request(target, url, param, remainText){
		var opts = $.data(target, 'combobox').options;
		if (url){
			opts.url = url;
		}
//		if (!opts.url) return;
		param = param || {};
		
		if (opts.onBeforeLoad.call(target, param) == false) return;

		opts.loader.call(target, param, function(data){
			loadData(target, data, remainText);
		}, function(){
			opts.onLoadError.apply(this, arguments);
		});
	}
	
	/**
	 * do the query action
	 */
	function doQuery(target, q){
		var state = $.data(target, 'combobox');
		var opts = state.options;
		
		if (opts.multiple && !q){
			setValues(target, [], true);
		} else {
			setValues(target, [q], true);
		}
		
		if (opts.mode == 'remote'){
			request(target, null, {q:q}, true);
		} else {
			var panel = $(target).combo('panel');
			panel.find('div.combobox-book-selected,div.combobox-book-hover').removeClass('combobox-book-selected combobox-book-hover');
			panel.find('div.combobox-book,div.combobox-group').hide();
			var data = state.data;
			var vv = [];
			var qq = opts.multiple ? q.split(opts.separator) : [q];
			$.map(qq, function(q){
				q = $.trim(q);
				var group = undefined;
				for(var i=0; i<data.length; i++){
					var row = data[i];
					if (opts.filter.call(target, q, row)){
						var v = row[opts.valueField];
						var s = row[opts.textField];
						var g = row[opts.groupField];
						var book = opts.finder.getEl(target, v).show();
						if (s.toLowerCase() == q.toLowerCase()){
							vv.push(v);
							book.addClass('combobox-book-selected');
						}
						if (opts.groupField && group != g){
							$('#'+state.groupIdPrefix+'_'+$.inArray(g, state.groups)).show();
							group = g;
						}
					}
				}
			});
			setValues(target, vv, true);
		}
	}
	
	function doEnter(target){
		var t = $(target);
		var opts = t.combobox('options');
		var panel = t.combobox('panel');
		var book = panel.children('div.combobox-book-hover');
		if (book.length){
			var row = opts.finder.getRow(target, book);
			var value = row[opts.valueField];
			if (opts.multiple){
				if (book.hasClass('combobox-book-selected')){
					t.combobox('unselect', value);
				} else {
					t.combobox('select', value);
				}
			} else {
				t.combobox('select', value);
			}
		}
		var vv = [];
		$.map(t.combobox('getValues'), function(v){
			if (getRowIndex(target, v) >= 0){
				vv.push(v);
			}
		});
		t.combobox('setValues', vv);
		if (!opts.multiple){
			t.combobox('hidePanel');
		}
	}
	
	/**
	 * create the component
	 */
	function create(target){
		var state = $.data(target, 'combobox');
		var opts = state.options;
		
		COMBOBOX_SERNO++;
		state.bookIdPrefix = '_easyui_combobox_i' + COMBOBOX_SERNO;
		state.groupIdPrefix = '_easyui_combobox_g' + COMBOBOX_SERNO;
		
		$(target).addClass('combobox-f');
		$(target).combo($.extend({}, opts, {
			onShowPanel: function(){
				$(target).combo('panel').find('div.combobox-book,div.combobox-group').show();
				scrollTo(target, $(target).combobox('getValue'));
				opts.onShowPanel.call(target);
			}
		}));
		
		$(target).combo('panel').unbind().bind('mouseover', function(e){
			$(this).children('div.combobox-book-hover').removeClass('combobox-book-hover');
			var book = $(e.target).closest('div.combobox-book');
			if (!book.hasClass('combobox-book-disabled')){
				book.addClass('combobox-book-hover');
			}
			e.stopPropagation();
		}).bind('mouseout', function(e){
			$(e.target).closest('div.combobox-book').removeClass('combobox-book-hover');
			e.stopPropagation();
		}).bind('click', function(e){
			var book = $(e.target).closest('div.combobox-book');
			if (!book.length || book.hasClass('combobox-book-disabled')){return}
			var row = opts.finder.getRow(target, book);
			if (!row){return}
			var value = row[opts.valueField];
			if (opts.multiple){
				if (book.hasClass('combobox-book-selected')){
					unselect(target, value);
				} else {
					select(target, value);
				}
			} else {
				select(target, value);
				$(target).combo('hidePanel');
			}
			e.stopPropagation();
		});
	}
	
	$.fn.combobox = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.combobox.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'combobox');
			if (state){
				$.extend(state.options, options);
				create(this);
			} else {
				state = $.data(this, 'combobox', {
					options: $.extend({}, $.fn.combobox.defaults, $.fn.combobox.parseOptions(this), options),
					data: []
				});
				create(this);
				var data = $.fn.combobox.parseData(this);
				if (data.length){
					loadData(this, data);
				}
			}
			if (state.options.data){
				loadData(this, state.options.data);
			}
			request(this);
		});
	};
	
	
	$.fn.combobox.methods = {
		options: function(jq){
			var copts = jq.combo('options');
			return $.extend($.data(jq[0], 'combobox').options, {
				width: copts.width,
				height: copts.height,
				originalValue: copts.originalValue,
				disabled: copts.disabled,
				readonly: copts.readonly
			});
		},
		getData: function(jq){
			return $.data(jq[0], 'combobox').data;
		},
		setValues: function(jq, values){
			return jq.each(function(){
				setValues(this, values);
			});
		},
		setValue: function(jq, value){
			return jq.each(function(){
				setValues(this, [value]);
			});
		},
		clear: function(jq){
			return jq.each(function(){
				$(this).combo('clear');
				var panel = $(this).combo('panel');
				panel.find('div.combobox-book-selected').removeClass('combobox-book-selected');
			});
		},
		reset: function(jq){
			return jq.each(function(){
				var opts = $(this).combobox('options');
				if (opts.multiple){
					$(this).combobox('setValues', opts.originalValue);
				} else {
					$(this).combobox('setValue', opts.originalValue);
				}
			});
		},
		loadData: function(jq, data){
			return jq.each(function(){
				loadData(this, data);
			});
		},
		reload: function(jq, url){
			return jq.each(function(){
				request(this, url);
			});
		},
		select: function(jq, value){
			return jq.each(function(){
				select(this, value);
			});
		},
		unselect: function(jq, value){
			return jq.each(function(){
				unselect(this, value);
			});
		}
	};
	
	$.fn.combobox.parseOptions = function(target){
		var t = $(target);
		return $.extend({}, $.fn.combo.parseOptions(target), $.parser.parseOptions(target,[
			'valueField','textField','groupField','mode','method','url'
		]));
	};
	
	$.fn.combobox.parseData = function(target){
		var data = [];
		var opts = $(target).combobox('options');
		$(target).children().each(function(){
			if (this.tagName.toLowerCase() == 'optgroup'){
				var group = $(this).attr('label');
				$(this).children().each(function(){
					_parseBook(this, group);
				});
			} else {
				_parseBook(this);
			}
		});
		return data;
		
		function _parseBook(el, group){
			var t = $(el);
			var row = {};
			row[opts.valueField] = t.attr('value')!=undefined ? t.attr('value') : t.text();
			row[opts.textField] = t.text();
			row['selected'] = t.is(':selected');
			row['disabled'] = t.is(':disabled');
			if (group){
				opts.groupField = opts.groupField || 'group';
				row[opts.groupField] = group;
			}
			data.push(row);
		}
	};
	
	$.fn.combobox.defaults = $.extend({}, $.fn.combo.defaults, {
		valueField: 'value',
		textField: 'text',
		groupField: null,
		groupFormatter: function(group){return group;},
		mode: 'local',	// or 'remote'
		method: 'post',
		url: null,
		data: null,
		
		keyHandler: {
			up: function(e){nav(this,'prev');e.preventDefault()},
			down: function(e){nav(this,'next');e.preventDefault()},
			left: function(e){},
			right: function(e){},
			enter: function(e){doEnter(this)},
			query: function(q,e){doQuery(this, q)}
		},
		filter: function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].toLowerCase().indexOf(q.toLowerCase()) == 0;
		},
		formatter: function(row){
			var opts = $(this).combobox('options');
			return row[opts.textField];
		},
		loader: function(param, success, error){
			var opts = $(this).combobox('options');
			if (!opts.url) return false;
			$.ajax({
				type: opts.method,
				url: opts.url,
				data: param,
				dataType: 'json',
				success: function(data){
					success(data);
				},
				error: function(){
					error.apply(this, arguments);
				}
			});
		},
		loadFilter: function(data){
			return data;
		},
		finder:{
			getEl:function(target, value){
				var index = getRowIndex(target, value);
				var id = $.data(target, 'combobox').bookIdPrefix + '_' + index;
				return $('#'+id);
			},
			getRow:function(target, p){
				var state = $.data(target, 'combobox');
				var index = (p instanceof jQuery) ? p.attr('id').substr(state.bookIdPrefix.length+1) : getRowIndex(target, p);
				return state.data[parseInt(index)];
			}
		},
		
		onBeforeLoad: function(param){},
		onLoadSuccess: function(){},
		onLoadError: function(){},
		onSelect: function(record){},
		onUnselect: function(record){}
	});
})(jQuery);

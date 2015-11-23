/**
 * flexselect: a jQuery plugin, version: 0.5.2 (2013-01-21)
 * @requires jQuery v1.3 or later
 *
 * FlexSelect is a jQuery plugin that makes it easy to convert a select box into
 * a Quicksilver-style, autocompleting, flex matching selection tool.
 *
 * For usage and examples, visit:
 * http://rmm5t.github.com/jquery-flexselect/
 *
 * Licensed under the MIT:
 * http://www.opensource.org/licenses/mit-license.php
 *
 * Copyright (c) 2009-2012, Ryan McGeary (ryan -[at]- mcgeary [*dot*] org)
 */
(function($) {
  $.flexselect = function(select, options) { this.init(select, options); };

  $.extend($.flexselect.prototype, {
    settings: {
      allowMismatch: false,
      allowMismatchBlank: true, // If "true" a user can backspace such that the value is nothing (even if no blank value was provided in the original criteria)
      sortBy: 'score', // 'score' || 'name'
      preSelection: true,
      hideDropdownOnEmptyInput: false,
      selectedClass: "flexselect_selected",
      dropdownClass: "flexselect_dropdown",
      inputIdTransform:    function(id)   { return id + "_flexselect"; },
      inputNameTransform:  function(name) { return; },
      dropdownIdTransform: function(id)   { return id + "_flexselect_dropdown"; }
    },
    select: null,
    input: null,
    dropdown: null,
    dropdownList: null,
    cache: [],
    results: [],
    lastAbbreviation: null,
    abbreviationBeforeFocus: null,
    selectedIndex: 0,
    picked: false,
    allowMouseMove: true,
    dropdownMouseover: false, // Workaround for poor IE behaviors

    init: function(select, options) {
      this.settings = $.extend({}, this.settings, options);
      this.select = $(select);
      this.preloadCache();
      this.renderControls();
      this.wire();
    },

    preloadCache: function() {
       //select下拉框赋值的地方
    	this.cache = this.select.children("option").map(function() {
        return { name: $.trim($(this).text()), value: $(this).val(), score: 0.0 ,id:$(this).attr("id")};
      });
    },

    renderControls: function() {
      var selected = this.settings.preSelection ? this.select.children("option:selected") : null;

	  this.input = $("<input type='text' autocomplete='off' class='flexselect-searchinput'/>");
	  this.input.attr({
        id: this.settings.inputIdTransform(this.select.attr("id")),
        name: this.settings.inputNameTransform(this.select.attr("name")),
        accesskey: this.select.attr("accesskey"),
        tabindex: this.select.attr("tabindex"),
        style: this.select.attr("style"),
        placeholder: this.select.attr("data-placeholder")
      }).addClass(this.select.attr("class")).val($.trim(selected ? selected.text():  '')).css({
        visibility: 'visible',
        display:'block'
      });

      this.dropdown = $("<div></div>").attr({
        id: this.settings.dropdownIdTransform(this.select.attr("id"))
      }).addClass(this.settings.dropdownClass);
      this.dropdownList = $("<ul></ul>");
      this.dropdown.append(this.dropdownList);

	  this.select.after("<div class='sel-more p-a'><div class='sel-search-box'><a class='search-btn1 fr mt2 mr4' href='javascript:void(0);'></a></div></div>").hide();
      this.select.next().find("a").after(this.input);
	  //显示搜索框及搜索按钮
	  this.input.prev().parent().parent().hide();
	  
	  //输入框宽度
	  var inputWidth = this.select.parent().prev().attr("width");
	  //默认输入框宽度
	  if(typeof(inputWidth) == "undefined")
	  {
		  inputWidth = "w75p";
	  }
	  //this.select.next().css("min-width",200 + "px");
	  this.select.next().addClass(inputWidth);
	  
	  //输入框左边距
	  var inputMarLeft = this.select.parent().parent().children().eq(0).width();
	  //默认输入框左边距
	  if(inputMarLeft == null || inputMarLeft == 8)
      {
    	  inputMarLeft = 65;
      }
	  //this.input.prev().parent().parent().css("left",(inputMarLeft-2)+"px");
	  this.input.prev().parent().parent().css("top",this.input.height());
	  this.input.prev().parent().parent().css("z-index","10");
	  //将下拉框追加到input框之下
	  this.select.parent().append(this.dropdown);
	  //下拉框的宽度
	  //this.dropdown.css("min-width",200 + "px");
	  this.dropdown.css("z-index","10");
	  this.dropdown.addClass(inputWidth);
	  
	  //$("body").append(this.dropdown);
    },

    wire: function() {
      var self = this;
      this.input.click(function() {
        self.lastAbbreviation = null;
        self.focus();
      });
     
      this.input.mouseup(function(event) {
        // This is so Safari selection actually occurs.
        event.preventDefault();
      });

      this.input.focus(function() {					
    	//显示搜索框及搜索按钮
		self.input.prev().parent().parent().show();
        self.abbreviationBeforeFocus = self.input.val();
        //如果重置数据，则需要将之前选择状态设置为第一条数据选中
        if(self.abbreviationBeforeFocus == "")
        {
        	self.filterResults();
        }
        //让焦点落在输入框最后一位
        //self.input.select();
        if (!self.picked) self.filterResults();
		//显示下拉框
		self.dropdown.show();
      });

      this.input.blur(function() {
    	  var searchValue = $.trim($(this).val());
    	  if (!self.dropdownMouseover) {
        	self.hide();
          if (self.settings.allowMismatchBlank && $.trim($(this).val()) == '')
        	  self.setValue('');
              self.input.attr("value",'');
          if (!self.settings.allowMismatch && !self.picked)
        	  //self.reset();
        	  //输入值不匹配
	          if(self.results.length <= 0)
	          {
	        	  //给输入框赋值
		          if(self.select.parent().prev().is("input"))
		          {
		        	  self.select.parent().prev().attr("value",'');
		          }
		          else
		          {
		        	  self.select.parent().siblings("input").eq(0).attr("value",'');
		          }
			  	  //select 隐藏区域后增加一个隐藏的input，给input框赋值
		          self.select.parent().next().val('');
		          self.input.attr("value",'');
	          }
	          else
	          {
	        	  //模糊搜索
	        	  //给输入框赋值
		          if(self.select.parent().prev().is("input"))
		          {
		        	  self.select.parent().prev().attr("value",searchValue);
		          }
		          else
		          {
		        	  self.select.parent().siblings("input").eq(0).attr("value",searchValue);
		          }
		          var selectedIndex = -1;
		          var selectId = "";
		          var count =  self.select.find("option").length;
		          for(var i=0;i<count;i++)  
		          {  
		        	  if($.trim(self.select.get(0).options[i].text) == $.trim(searchValue))  
		              {  
		        	    	selectedIndex = i;
		        	    	selectId = self.select.find("option").eq(i).attr("id");
		              }
		          }
		          //select 隐藏区域后增加一个隐藏的input，给input框赋值
		    	  //如果没有选择任何一项
		    	  if(searchValue != "" && selectedIndex != -1)
		    	  {
		    		  self.select.parent().next().val(selectId);
		    	  }
		    	  else
		    	  {
		    		  self.select.parent().next().val("");
		    	  }
		          self.input.attr("value",searchValue)
		          if(self.input.attr("id") == "carModel_flexselect_flexselect")//添加车型品牌
		  		  {
		  			  //该变图片
		  			  changeImage();
		  		  }
		  		  else if(self.input.attr("id") == "facilityBindingData_flexselect_flexselect")//设备
		  		  {
		  			  //调用后台查询设备号的信息
		  			  query();
		  		  }
		  		  else if(self.input.attr("id") == "carModel_edit_flexselect_flexselect")//编辑车型品牌
		  		  {
						//该变图片
						changeImage();
						//车型品牌
						judgeCarModel();
				 }
		  		 else if(self.input.attr("id") == "stopStoredId_flexselect_flexselect")//停靠门店
		  		 {
						//停靠门店
						judgeStopStoreId();
				 }
		  		 else if(self.input.attr("id") == "storedId_flexselect_flexselect")//所属门店
		  		 {
						//所属门店
						judgeStoreId();
		  		 }
	          }
        }
        if (self.settings.hideDropdownOnEmptyInput)
          self.dropdownList.show();
      });

      this.dropdownList.mouseover(function(event) {
        if (!self.allowMouseMove) {
          self.allowMouseMove = true;
          return;
        }

        if (event.target.tagName == "LI") {
          var rows = self.dropdown.find("li");
          self.markSelected(rows.index($(event.target)));
        }
      });
      this.dropdownList.mouseleave(function() {
        self.markSelected(-1);
      });
      this.dropdownList.mouseup(function(event) {
        self.pickSelected();
        self.focusAndHide();
      });
      this.dropdown.mouseover(function(event) {
        self.dropdownMouseover = true;
      });
      this.dropdown.mouseleave(function(event) {
        self.dropdownMouseover = false;
      });
      this.dropdown.mousedown(function(event) {
        event.preventDefault();
      });

      this.input.keyup(function(event) {
        switch (event.keyCode) {
          case 13: // return
            event.preventDefault();
            self.pickSelected();
            self.focusAndHide();
            break;
          case 27: // esc
            event.preventDefault();
            self.reset();
            self.focusAndHide();
            break;
          default:
            self.filterResults();
            break;
        }
        if (self.settings.hideDropdownOnEmptyInput){
          if(self.input.val() == "")
            self.dropdownList.hide();
          else
            self.dropdownList.show();
        }
      });

      this.input.keydown(function(event) {
        switch (event.keyCode) {
          case 9:  // tab
            self.pickSelected();
            self.hide();
            break;
          case 33: // pgup
            event.preventDefault();
            self.markFirst();
            break;
          case 34: // pgedown
            event.preventDefault();
            self.markLast();
            break;
          case 38: // up
            event.preventDefault();
            self.moveSelected(-1);
            break;
          case 40: // down
            event.preventDefault();
            self.moveSelected(1);
            break;
          case 13: // return
          case 27: // esc
            event.preventDefault();
            event.stopPropagation();
            break;
        }
      });

      var input = this.input;
      this.select.change(function () {
        input.val($.trim($(this).children('option:selected').text()));
      });
    },

    filterResults: function() {
      var abbreviation = this.input.val();
      if (abbreviation == this.lastAbbreviation) return;

      var results = [];
      $.each(this.cache, function() {
        this.score = LiquidMetal.score(this.name, abbreviation);
        if (this.score > 0.0) results.push(this);
      });
      this.results = results;

      if (this.settings.sortBy == 'score')
        this.sortResultsByScore();
      else if (this.settings.sortBy == 'name')
        this.sortResultsByName();

      this.renderDropdown(); 
      this.markFirst();
      this.lastAbbreviation = abbreviation;
      this.picked = false;
      this.allowMouseMove = false;
    },

    sortResultsByScore: function() {
      this.results.sort(function(a, b) { return b.score - a.score; });
    },

    sortResultsByName: function() {
      this.results.sort(function(a, b) { return a.name < b.name ? -1 : (a.name > b.name ? 1 : 0); });
    },

    renderDropdown: function() {
      var dropdownBorderWidth = this.dropdown.outerWidth() - this.dropdown.innerWidth();
      var inputOffset = this.input.parent().offset();
      //两个输入框之和
      var dropDownTop = this.select.next().height()+this.select.parent().parent().height();
      //span的文字长度
      var dropDownWidth = this.select.parent().parent().children().eq(0).width();
      //默认下拉框左边距
      if(dropDownWidth == null || dropDownWidth == 8)
      {
    	  dropDownWidth = 65;
      }
      //下拉框的样式
      this.dropdown.css({
		//width: ((this.input.prev().parent().outerWidth()) + 10)  + "px",
		top: dropDownTop,
		left: 2 + "px", 
		maxHeight: ''
	  }); 
   
      var html = '';

      $.each(this.results, function() {
        html += "<li style='width:100%'>" + this.name + "</li>";
      });
      //没有符合的数据，提示“无查找结果”
      if(this.results.length <= 0)
	  {
    	  html = "<li style='width:100%'>无查找结果</li>";
	  }
      this.dropdownList.html(html);
      this.adjustMaxHeight();
      this.dropdown.show();
    },

    adjustMaxHeight: function() {
      var maxTop = $(window).height() + $(window).scrollTop() - this.dropdown.outerHeight();
      var top = parseInt(this.dropdown.css('top'), 10);
      this.dropdown.css('max-height', top > maxTop ? (Math.max(0, maxTop - top + this.dropdown.innerHeight()) + 'px') : '');
    },

    markSelected: function(n) {
      if (n < 0 || n >= this.results.length) return;

      var rows = this.dropdown.find("li");
      rows.removeClass(this.settings.selectedClass);
      this.selectedIndex = n;

      var row = $(rows[n]).addClass(this.settings.selectedClass);
      var top = row.position().top;
      var delta = top + row.outerHeight() - this.dropdown.height();
      if (delta > 0) {
        this.allowMouseMove = false;
        this.dropdown.scrollTop(this.dropdown.scrollTop() + delta);
      } else if (top < 0) {
        this.allowMouseMove = false;
        this.dropdown.scrollTop(Math.max(0, this.dropdown.scrollTop() + top));
      }
    },

    pickSelected: function() {
      var selected = this.results[this.selectedIndex];
      if (selected) {
    	this.input.attr("value",selected.name);
		//选择内容后隐藏此搜索框
		this.input.hide();
        this.setValue(selected.value);
		//给输入框赋值
        if(this.select.parent().prev().is("input"))
        {
        	this.select.parent().prev().attr("value",selected.name);
        }
        else
        {
        	this.select.parent().siblings("input").eq(0).attr("value",selected.name);
        }
		//select 隐藏区域后增加一个隐藏的input，给input框赋值
		this.select.parent().next().val(selected.id);
		if(this.input.attr("id") == "carModel_flexselect_flexselect")//添加车型品牌
		  {
			  //该变图片
			  changeImage();
		  }
		  else if(this.input.attr("id") == "facilityBindingData_flexselect_flexselect")//设备
		  {
			  //调用后台查询设备号的信息
			  query();
		  }
		  else if(this.input.attr("id") == "carModel_edit_flexselect_flexselect")//编辑车型品牌
		  {
				//该变图片
				changeImage();
				//车型品牌
				judgeCarModel();
		 }
		 else if(this.input.attr("id") == "stopStoredId_flexselect_flexselect")//停靠门店
		 {
				//停靠门店
				judgeStopStoreId();
		 }
		 else if(this.input.attr("id") == "storedId_flexselect_flexselect")//所属门店
		 {
				//所属门店
				judgeStoreId();
		 }
        this.picked = true;
      } else if (this.settings.allowMismatch) {
    	  this.setValue.val("");
      } else {
    	  this.reset();
      }
    },

    setValue: function(val) {
      if (this.select.val() === val) return;
      this.select.val(val).change();
      //当输入框值清空后，则显示为空
      if(val == "")
      {
          if(this.select.parent().prev().is("input"))
          {
          	this.select.parent().prev().attr("value","");
          	//select 隐藏区域后增加一个隐藏的input，给input框赋值
    		this.select.parent().next().attr("value","");
          }
          else
          {
          	this.select.parent().prev().prev().attr("value","");
          	//select 隐藏区域后增加一个隐藏的input，给input框赋值
    		this.select.parent().next().attr("value","");
          }
          this.abbreviationBeforeFocus = "";
      }
    },

    hide: function() {
      this.dropdown.hide();
      this.lastAbbreviation = null;
	  //关闭下拉搜索框
	  this.input.prev().parent().parent().hide();
    },

    moveSelected: function(n) { this.markSelected(this.selectedIndex+n); },
    markFirst:    function()  { this.markSelected(0); },
    markLast:     function()  { this.markSelected(this.results.length - 1); },
    reset:        function()  { this.input.val(this.abbreviationBeforeFocus); },
    focus:        function()  { this.input.focus(); },
    focusAndHide: function()  { this.focus(); this.hide(); }
  });

  $.fn.flexselect = function(options) {
    this.each(function() {
      if (this.tagName == "SELECT") new $.flexselect(this, options);
    });
    return this;
  };
})(jQuery);  

(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-index-index~pages-setup-setup"],{"024f":function(t,n,e){"use strict";e.r(n);var i=e("cba5"),u=e.n(i);for(var a in i)["default"].indexOf(a)<0&&function(t){e.d(n,t,(function(){return i[t]}))}(a);n["default"]=u.a},"0346":function(t,n,e){"use strict";e("6a54");var i=e("f5bd").default;Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var u=i(e("2634")),a=i(e("2fdc"));e("64aa"),e("bf0f");var r=i(e("a793")),s={name:"u-row",mixins:[uni.$u.mpMixin,uni.$u.mixin,r.default],data:function(){return{}},computed:{uJustify:function(){return"end"==this.justify||"start"==this.justify?"flex-"+this.justify:"around"==this.justify||"between"==this.justify?"space-"+this.justify:this.justify},uAlignItem:function(){return"top"==this.align?"flex-start":"bottom"==this.align?"flex-end":this.align},rowStyle:function(){var t={alignItems:this.uAlignItem,justifyContent:this.uJustify};return this.gutter&&(t.marginLeft=uni.$u.addUnit(-Number(this.gutter)/2),t.marginRight=uni.$u.addUnit(-Number(this.gutter)/2)),uni.$u.deepMerge(t,uni.$u.addStyle(this.customStyle))}},methods:{clickHandler:function(t){this.$emit("click")},getComponentWidth:function(){var t=this;return(0,a.default)((0,u.default)().mark((function n(){return(0,u.default)().wrap((function(n){while(1)switch(n.prev=n.next){case 0:return n.next=2,uni.$u.sleep();case 2:return n.abrupt("return",new Promise((function(n){t.$uGetRect(".u-row").then((function(t){n(t.width)}))})));case 3:case"end":return n.stop()}}),n)})))()}}};n.default=s},"0d0d":function(t,n,e){"use strict";e.r(n);var i=e("0346"),u=e.n(i);for(var a in i)["default"].indexOf(a)<0&&function(t){e.d(n,t,(function(){return i[t]}))}(a);n["default"]=u.a},"0fb0":function(t,n,e){var i=e("c86c");n=i(!1),n.push([t.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */uni-view[data-v-91d5fe04], uni-scroll-view[data-v-91d5fe04], uni-swiper-item[data-v-91d5fe04]{display:flex;flex-direction:column;flex-shrink:0;flex-grow:0;flex-basis:auto;align-items:stretch;align-content:flex-start}.u-col[data-v-91d5fe04]{padding:0;box-sizing:border-box}.u-col-0[data-v-91d5fe04]{width:0}.u-col-1[data-v-91d5fe04]{width:calc(100%/12)}.u-col-2[data-v-91d5fe04]{width:calc(100%/12 * 2)}.u-col-3[data-v-91d5fe04]{width:calc(100%/12 * 3)}.u-col-4[data-v-91d5fe04]{width:calc(100%/12 * 4)}.u-col-5[data-v-91d5fe04]{width:calc(100%/12 * 5)}.u-col-6[data-v-91d5fe04]{width:calc(100%/12 * 6)}.u-col-7[data-v-91d5fe04]{width:calc(100%/12 * 7)}.u-col-8[data-v-91d5fe04]{width:calc(100%/12 * 8)}.u-col-9[data-v-91d5fe04]{width:calc(100%/12 * 9)}.u-col-10[data-v-91d5fe04]{width:calc(100%/12 * 10)}.u-col-11[data-v-91d5fe04]{width:calc(100%/12 * 11)}.u-col-12[data-v-91d5fe04]{width:calc(100%/12 * 12)}',""]),t.exports=n},"52cd":function(t,n,e){var i=e("9b18");i.__esModule&&(i=i.default),"string"===typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);var u=e("967d").default;u("9227b98e",i,!0,{sourceMap:!1,shadowMode:!1})},7353:function(t,n,e){"use strict";e.r(n);var i=e("ec56"),u=e("0d0d");for(var a in u)["default"].indexOf(a)<0&&function(t){e.d(n,t,(function(){return u[t]}))}(a);e("be9f");var r=e("828b"),s=Object(r["a"])(u["default"],i["b"],i["c"],!1,null,"3fa5722e",null,!1,i["a"],void 0);n["default"]=s.exports},"97c1":function(t,n,e){"use strict";e("6a54"),Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0,e("64aa");var i={props:{span:{type:[String,Number],default:uni.$u.props.col.span},offset:{type:[String,Number],default:uni.$u.props.col.offset},justify:{type:String,default:uni.$u.props.col.justify},align:{type:String,default:uni.$u.props.col.align},textAlign:{type:String,default:uni.$u.props.col.textAlign}}};n.default=i},"9b18":function(t,n,e){var i=e("c86c");n=i(!1),n.push([t.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */uni-view[data-v-3fa5722e], uni-scroll-view[data-v-3fa5722e], uni-swiper-item[data-v-3fa5722e]{display:flex;flex-direction:column;flex-shrink:0;flex-grow:0;flex-basis:auto;align-items:stretch;align-content:flex-start}.u-row[data-v-3fa5722e]{display:flex;flex-direction:row}',""]),t.exports=n},a793:function(t,n,e){"use strict";e("6a54"),Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0,e("64aa");var i={props:{gutter:{type:[String,Number],default:uni.$u.props.row.gutter},justify:{type:String,default:uni.$u.props.row.justify},align:{type:String,default:uni.$u.props.row.align}}};n.default=i},bc17:function(t,n,e){var i=e("0fb0");i.__esModule&&(i=i.default),"string"===typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);var u=e("967d").default;u("5e941676",i,!0,{sourceMap:!1,shadowMode:!1})},be9f:function(t,n,e){"use strict";var i=e("52cd"),u=e.n(i);u.a},c5d0:function(t,n,e){"use strict";var i=e("bc17"),u=e.n(i);u.a},cba5:function(t,n,e){"use strict";e("6a54");var i=e("f5bd").default;Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var u=i(e("2634")),a=i(e("2fdc")),r=i(e("97c1")),s={name:"u-col",mixins:[uni.$u.mpMixin,uni.$u.mixin,r.default],data:function(){return{width:0,parentData:{gutter:0},gridNum:12}},computed:{uJustify:function(){return"end"==this.justify||"start"==this.justify?"flex-"+this.justify:"around"==this.justify||"between"==this.justify?"space-"+this.justify:this.justify},uAlignItem:function(){return"top"==this.align?"flex-start":"bottom"==this.align?"flex-end":this.align},colStyle:function(){var t={paddingLeft:uni.$u.addUnit(uni.$u.getPx(this.parentData.gutter)/2),paddingRight:uni.$u.addUnit(uni.$u.getPx(this.parentData.gutter)/2),alignItems:this.uAlignItem,justifyContent:this.uJustify,textAlign:this.textAlign,flex:"0 0 ".concat(100/this.gridNum*this.span,"%"),marginLeft:100/12*this.offset+"%"};return uni.$u.deepMerge(t,uni.$u.addStyle(this.customStyle))}},mounted:function(){this.init()},methods:{init:function(){var t=this;return(0,a.default)((0,u.default)().mark((function n(){return(0,u.default)().wrap((function(n){while(1)switch(n.prev=n.next){case 0:return t.updateParentData(),n.next=3,t.parent.getComponentWidth();case 3:t.width=n.sent;case 4:case"end":return n.stop()}}),n)})))()},updateParentData:function(){this.getParentData("u-row")},clickHandler:function(t){this.$emit("click")}}};n.default=s},d32f:function(t,n,e){"use strict";e.d(n,"b",(function(){return i})),e.d(n,"c",(function(){return u})),e.d(n,"a",(function(){}));var i=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("v-uni-view",{ref:"u-col",staticClass:"u-col",class:["u-col-"+t.span],style:[t.colStyle],on:{click:function(n){arguments[0]=n=t.$handleEvent(n),t.clickHandler.apply(void 0,arguments)}}},[t._t("default")],2)},u=[]},ec56:function(t,n,e){"use strict";e.d(n,"b",(function(){return i})),e.d(n,"c",(function(){return u})),e.d(n,"a",(function(){}));var i=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("v-uni-view",{ref:"u-row",staticClass:"u-row",style:[t.rowStyle],on:{click:function(n){arguments[0]=n=t.$handleEvent(n),t.clickHandler.apply(void 0,arguments)}}},[t._t("default")],2)},u=[]},ef3d:function(t,n,e){"use strict";e.r(n);var i=e("d32f"),u=e("024f");for(var a in u)["default"].indexOf(a)<0&&function(t){e.d(n,t,(function(){return u[t]}))}(a);e("c5d0");var r=e("828b"),s=Object(r["a"])(u["default"],i["b"],i["c"],!1,null,"91d5fe04",null,!1,i["a"],void 0);n["default"]=s.exports}}]);
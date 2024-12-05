<template>
	<view class="container">
		<u-notify ref="uNotify"></u-notify>
		<view class="box-bg">
			<uniNavBar height='44' :fixed="true" @clickLeft="handleReturn()" @clickRight="handlepreview()"
				:border="false" shadow left-icon="left" leftText="返回" rightText="预览" title="新闻稿">
			</uniNavBar>
		</view>
		<view class="page-body">
			<view class="header">
				<u-gap height="10"></u-gap>
				<view class="title">
					<view class="input">
						<input @blur="save('title')" type="text" class="title-input" focus placeholder="标题"
							placeholder-style="font-size:20px" v-model="title"></input>
					</view>
					<view class="picker" @click="selectPicture()">
						<view v-if='!coverImage' class="upload-view">
							<uni-icons type="plusempty" size="20"></uni-icons>
							<view class="text"> 上传封面图 </view>
						</view>
						<image class="cover-image" mode="scaleToFill" v-if='coverImage' :src="imgPath + coverImage">
						</image>
					</view>
				</view>
				<u-gap height="10"></u-gap>
				<view style="">
					<u-line length="92%" margin="auto" direction="row"></u-line>
				</view>
			</view>
			<view class="editor-wrapper">
				<editor id="editor" class="ql-container" placeholder="内容..." show-img-size show-img-toolbar
					show-img-resize @input="save('text')" @statuschange="onStatusChange" :read-only="readOnly"
					@ready="onEditorReady">
				</editor>
			</view>
			<view class='wrapper'>
				<view class='toolbar' @tap="format" style="height: 80px;overflow-y: auto;">
					<view :class="formats.bold ? 'ql-active' : ''" class="iconfont icon-zitijiacu" data-name="bold">
					</view>
					<view :class="formats.italic ? 'ql-active' : ''" class="iconfont icon-zitixieti" data-name="italic">
					</view>
					<view :class="formats.underline ? 'ql-active' : ''" class="iconfont icon-zitixiahuaxian"
						data-name="underline"></view>
					<view :class="formats.strike ? 'ql-active' : ''" class="iconfont icon-zitishanchuxian"
						data-name="strike"></view>
					<!-- #ifndef MP-BAIDU -->
					<view :class="formats.align === 'left' ? 'ql-active' : ''" class="iconfont icon-zuoduiqi"
						data-name="align" data-value="left"></view>
					<!-- #endif -->
					<view :class="formats.align === 'center' ? 'ql-active' : ''" class="iconfont icon-juzhongduiqi"
						data-name="align" data-value="center"></view>
					<view :class="formats.align === 'right' ? 'ql-active' : ''" class="iconfont icon-youduiqi"
						data-name="align" data-value="right"></view>
					<view :class="formats.align === 'justify' ? 'ql-active' : ''" class="iconfont icon-zuoyouduiqi"
						data-name="align" data-value="justify"></view>
					<!-- #ifndef MP-BAIDU -->
					<view :class="formats.lineHeight ? 'ql-active' : ''" class="iconfont icon-line-height"
						data-name="lineHeight" data-value="2"></view>
					<view :class="formats.letterSpacing ? 'ql-active' : ''" class="iconfont icon-Character-Spacing"
						data-name="letterSpacing" data-value="2em"></view>
					<view :class="formats.marginTop ? 'ql-active' : ''" class="iconfont icon-722bianjiqi_duanqianju"
						data-name="marginTop" data-value="20px"></view>
					<view :class="formats.marginBottom ? 'ql-active' : ''" class="iconfont icon-723bianjiqi_duanhouju"
						data-name="marginBottom" data-value="20px"></view>
					<!-- #endif -->

					<view class="iconfont icon-clearedformat" @tap="removeFormat"></view>

					<!-- #ifndef MP-BAIDU -->
					<view :class="formats.fontFamily ? 'ql-active' : ''" class="iconfont icon-font"
						data-name="fontFamily" data-value="Pacifico"></view>
					<view :class="formats.fontSize === '24px' ? 'ql-active' : ''" class="iconfont icon-fontsize"
						data-name="fontSize" data-value="24px"></view>
					<!-- #endif -->
					<view :class="formats.color === '#0000ff' ? 'ql-active' : ''" class="iconfont icon-text_color"
						data-name="color" data-value="#0000ff"></view>
					<view :class="formats.backgroundColor === '#00ff00' ? 'ql-active' : ''"
						class="iconfont icon-fontbgcolor" data-name="backgroundColor" data-value="#00ff00"></view>
					<view class="iconfont icon-date" @tap="insertDate"></view>
					<view class="iconfont icon--checklist" data-name="list" data-value="check"></view>
					<view :class="formats.list === 'ordered' ? 'ql-active' : ''" class="iconfont icon-youxupailie"
						data-name="list" data-value="ordered"></view>
					<view :class="formats.list === 'bullet' ? 'ql-active' : ''" class="iconfont icon-wuxupailie"
						data-name="list" data-value="bullet"></view>

					<view class="iconfont icon-undo" @tap="undo"></view>
					<view class="iconfont icon-redo" @tap="redo"></view>

					<view class="iconfont icon-outdent" data-name="indent" data-value="-1"></view>
					<view class="iconfont icon-indent" data-name="indent" data-value="+1"></view>
					<view class="iconfont icon-fengexian" @tap="insertDivider"></view>
					<view class="iconfont icon-charutupian" @tap="insertImage"></view>
					<view :class="formats.header === 1 ? 'ql-active' : ''" class="iconfont icon-format-header-1"
						data-name="header" :data-value="1"></view>
					<view :class="formats.script === 'sub' ? 'ql-active' : ''" class="iconfont icon-zitixiabiao"
						data-name="script" data-value="sub"></view>
					<view :class="formats.script === 'super' ? 'ql-active' : ''" class="iconfont icon-zitishangbiao"
						data-name="script" data-value="super"></view>

					<view class="iconfont icon-shanchu" @tap="clear"></view>

					<view :class="formats.direction === 'rtl' ? 'ql-active' : ''" class="iconfont icon-direction-rtl"
						data-name="direction" data-value="rtl"></view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import uniNavBar from '@/uni_modules/uni-nav-bar/components/uni-nav-bar/uni-nav-bar.vue';
	export default {
		components: {
			uniNavBar,
		},
		data() {
			return {
				readOnly: false,
				// 标题
				title: '',
				formats: {},
				// 封面路径
				coverImage: '',
				imgPath: '/static/uploads/images/',
				text: '',
				// 是否重新编辑 ，1为是，0为否
				reEdit: 0
			}
		},
		async onLoad(options) {
			console.log(options);
			// await this.handleReEdit(+options.id);
			this.getdata();
			console.log(33333333333333333333333333);
		},
		onShow() {
			this.coverImage = uni.getStorageSync('cropImageName');
		},
		// 初始化富文本内容
		methods: {
			getdata() {
				this.coverImage = uni.getStorageSync('cropImageName');
				this.title = uni.getStorageSync('title');
				this.text = uni.getStorageSync('text');
			},
			async handleReEdit(reEdit) {
				console.log("reEdit" + reEdit);
				this.reEdit = reEdit;
				// 如果不是重新编辑
				if (isNaN(reEdit) || reEdit === 0) {
					await this.judge();
					this.coverImage = uni.getStorageSync('cropImageName');
					console.log(this.coverImage);
					this.title = uni.getStorageSync('title');
					this.text = uni.getStorageSync('text');
				} else {
					this.reEdit = 1;
					const path = '/news/myself';
					const res = await uni.request({
						url: getApp().globalData.URL + path,
						method: 'GET',
						header: {
							'token': uni.getStorageSync('token'),
						},
					});
					console.log(res);
					const data = res.data.data;
					console.log(data);
					this.coverImage = data.coverImage;
					this.save('coverImage');
					this.title = data.title;
					this.save('title');
					this.text = data.text;
					this.editorCtx.setContents({
						html: this.text,
					})
					this.save('text');
					console.log(this);
				}
				console.log(this.reEdit);
			},
			// 判断是否存在
			async judge() {
				const path = '/news/judge';
				const res = await uni.request({
					url: getApp().globalData.URL + path,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
				})

				console.log(res);
				// 是否有权限上传新闻稿
				if (res.data.code === 0) {
					this.notify('error', res.data.msg);
					return;
				}
				const judge = res.data.data;
				// 不存在 judge = 0
				// 新闻稿已存在
				if (judge != 0) {
					uni.redirectTo({
						url: getApp().globalData.pagePath.newsFinish,
					})
				}
			},
			// 处理点击预览
			handlepreview() {
				uni.navigateTo({
					url: getApp().globalData.pagePath.preview,
				})
			},
			// 自定义导航栏返回处理
			handleReturn() {
				uni.navigateBack();
			},
			// 保存内容
			save(content) {
				switch (content) {
					case 'title':
						uni.setStorageSync('title', this.title);
						break;
					case 'text':
						this.text = this.getEditorContent();
						uni.setStorageSync('text', this.text);
						break;
					case 'coverImage':
						uni.setStorageSync('cropImageName', this.coverImage);
				}

			},
			// 跳转到裁剪页面
			async toCrop() {
				await uni.navigateTo({
					url: getApp().globalData.pagePath.crop,
				})
			},
			// 实现选择并上传图片的功能
			async uploadImage() {
				const path = '/files/images';
				let imgData = await uni.chooseImage({
					count: 1,
				})
				console.log(imgData);
				const tempFilePath = imgData.tempFilePaths[0];
				console.log(tempFilePath);
				let uploadData = await uni.uploadFile({
					url: getApp().globalData.URL + path,
					filePath: tempFilePath,
					name: 'img',
					header: {
						'token': uni.getStorageSync('token'),
					},
				})
				console.log(uploadData);
				const imgName = JSON.parse(uploadData.data).data;
				return imgName;
			},
			// 点击上传封面图时触发
			async selectPicture() {
				await this.toCrop();
				// const imgName = this.uploadImage();
				// data返回图片名
				// this.coverImage = getApp().globalData.imgPath + imgName;
			},
			// 获取富文本html内容
			getEditorContent() {
				let html;

				this.editorCtx.getContents({
					success: function(res) {
						html = res.html;
						console.log(html);
						console.log(res.delta);
					}
				})
				return html;
			},
			/* -------------富文本编辑器功能实现---------------------- */
			onEditorReady() {

				// #ifdef MP-BAIDU
				this.editorCtx = requireDynamicLib('editorLib').createEditorContext('editor');
				// #endif

				// #ifdef APP-PLUS || MP-WEIXIN || H5
				uni.createSelectorQuery().select('#editor').context((res) => {
					this.editorCtx = res.context
				}).exec()
				// #endif
				this.editorCtx.setContents({
					html: this.text,
				})
			},
			undo() {
				this.editorCtx.undo()
			},
			redo() {
				this.editorCtx.redo()
			},
			format(e) {
				let {
					name,
					value
				} = e.target.dataset
				if (!name) return
				// console.log('format', name, value)
				this.editorCtx.format(name, value)
			},
			onStatusChange(e) {
				const formats = e.detail
				this.formats = formats
			},
			insertDivider() {
				this.editorCtx.insertDivider({
					success: function() {
						console.log('insert divider success')
					}
				})
			},
			clear() {
				uni.showModal({
					title: '清空编辑器',
					content: '确定清空编辑器全部内容？',
					success: res => {
						if (res.confirm) {
							this.editorCtx.clear({
								success: (res) => {
									this.text = '';
									uni.setStorageSync('text', this.text);
									console.log("clear success");
								}
							})
						}
					}
				})
			},
			removeFormat() {
				this.editorCtx.removeFormat()
			},
			insertDate() {
				const date = new Date()
				const formatDate = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`
				this.editorCtx.insertText({
					text: formatDate
				})
			},
			async insertImage() {
				const imgName = await this.uploadImage();
				this.editorCtx.insertImage({
					src: getApp().globalData.imgPath + imgName,
					alt: '图像',
					success: function() {
						console.log('insert image success')
					}
				})
			},
			// 错误提示
			notify(type, message) {
				this.$refs.uNotify.show({
					top: 10,
					type,
					message,
					duration: 1000 * 3,
					fontSize: 20,
				})
			}
		},
	}
</script>


<style lang="less" scoped>
	@import "/pages/student/news/editor-icon.css";


	.container {
		/* 自定义导航栏高度：44px，wrapper 工具的高度:80px，header的高度:70px */
		--top-nav-bar: 44px;
		--wrapper-tools-height: 80px;
		--header-height: 70px;
		height: calc(100vh - var(--top-nav-bar));
	}


	.header {
		height: var(--header-height);
	}

	.title {
		display: flex;
		width: 92%;
		// height: 100px;
		margin: auto;
		align-items: center;

	}

	.input {
		flex-grow: 1;
		font-size: 20px;
		font-weight: bold;
		height: 100%;
	}

	.page-body {
		height: calc(100vh - var(--top-nav-bar) - var(--status-bar-height));
	}

	.picker {
		// background-color: red;
		min-width: 90px;
		max-width: 90px;
		height: 50px;
		margin-left: 10px;
		border: #EEE 2px solid;
	}

	.cover-image {
		width: 90px;
		height: 50px;
	}

	.upload-view {
		background-color: WhiteSmoke;
		// width: 70px;
		min-width: 90px;
		min-height: 50px;
		height: 50px;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;


		.text {
			font-size: 12px;
		}

	}

	/* 	.wrapper {
		height: 100%;
	} */

	.editor-wrapper {
		height: calc(100vh - var(--top-nav-bar) - var(--status-bar-height) - var(--wrapper-tools-height) - var(--header-height));
		background: #fff;
	}

	.iconfont {
		display: inline-block;
		padding: 8px 8px;
		width: 24px;
		height: 24px;
		cursor: pointer;
		font-size: 20px;
	}

	.toolbar {
		box-sizing: border-box;
		border-bottom: 0;
		font-family: 'Helvetica Neue', 'Helvetica', 'Arial', sans-serif;
	}

	.ql-container {
		box-sizing: border-box;
		padding: 12px 15px;
		width: 100%;
		min-height: 30vh;
		height: 100%;
		/* margin-top: 20px; */
		font-size: 16px;
		line-height: 1.5;
	}

	.ql-active {
		color: #06c;
	}
</style>
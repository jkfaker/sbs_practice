<template>
	<view class="container">
		<u-notify ref="uNotify"></u-notify>
		<view class="box-bg">
			<uniNavBar height='44' :fixed="true" @clickLeft="handleReturn()" @clickRight="handleSend()" :border="false"
				shadow left-icon="left" leftText="返回" rightText="发布" title="编辑通告">
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
					<view class="picker" @click="getFile()">
						<view v-if='!fileName' class="upload-view">
							<uni-icons type="upload" size="20"></uni-icons>
							<view class="text"> 上传附件 </view>
						</view>
						<view v-if='fileName' class="upload-view">
							<uni-icons type="folder-add-filled" size="20"></uni-icons>
							<view class="text"> 文件已上传 </view>
						</view>
					</view>
				</view>
				<u-gap height="10"></u-gap>
				<view style="">
					<u-line length="92%" margin="auto" direction="row"></u-line>
				</view>
			</view>
			<view class="editor-wrapper">
				<editor id="editor" class="ql-container" @input="save('text')" placeholder="内容..." show-img-size
					show-img-toolbar show-img-resize @statuschange="onStatusChange" :read-only="readOnly"
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
				imgPath: '/static/uploads/images',
				text: '',
				// 老师上传文件
				file: '',
				//从后端返回回来的文件名
				fileName: ''
			}
		},
		async onShow(options) {
			console.log(options);
			this.title = uni.getStorageSync('title');
			this.text = uni.getStorageSync('text');
			this.fileName = uni.getStorageSync('fileName');
		},
		methods: {
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
					case 'fileName':
						uni.setStorageSync('fileName', this.fileName);
				}
			},
			getFile() {
				uni.chooseFile({
					count: 1,
					type: 'all',
					extension: ['.zip', '.rar', '.7z', '.tar', 'gz', '.tgz'],
					success: (res) => {
						console.log(res);
						this.file = res.tempFiles[0].path;
						this.uploadFile();
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})
			},
			uploadFile() {
				const PATH = '/teacher/notice/files/upload';
				// const formData = new FormData();

				// formData.append('project', JSON.stringify(this.project.project));
				// formData.append('members', JSON.stringify(this.project.members));
				uni.uploadFile({
					url: getApp().globalData.URL + PATH,
					filePath: this.file,
					name: 'file',
					header: {
						'token': uni.getStorageSync('token')
					},
					success: (res) => {
						console.log(res);
						res.data = JSON.parse(res.data);
						if (res.data.code === 0) {
							// 报错,返回报错信息
							this.notify('error', res.data.msg);
							return;
						}
						this.fileName = res.data.data;
						this.save('fileName');
						this.notify('success', '文件上传成功');
					},
					fail: (error) => {
						this.loading = false;
						// 弹弹框
						this.notify('error', '网络异常,请稍后重试');
						console.log(error);
					}
				})
				this.modalShow = false;
				this.loading = true;
			},
			removeStorage() {
				this.title = uni.removeStorageSync('title');
				this.text = uni.removeStorageSync('text');
				this.fileName = uni.removeStorageSync('fileName');
			},
			// 处理点击发送
			handleSend() {
				const PATH = '/teacher/notice/add';
				uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'POST',
					header: {
						'token': uni.getStorageSync('token'),
					},
					data: {
						title: this.title,
						text: this.getEditorContent(),
						fileName: this.fileName,
					},
					success: (res) => {
						console.log(res);
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						this.notify('success', '发布成功');
						this.removeStorage();
						setTimeout(() => {
							uni.navigateTo({
								url: getApp().globalData.pagePath.tNotice,
							})
						}, 1000);
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}


				})
			},
			// 自定义导航栏返回处理
			handleReturn() {
				uni.navigateBack();
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
						this.text = getEditorContent();
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
	@import "./editor-icon.css";


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

		.input {
			flex-grow: 1;
			font-size: 20px;
			font-weight: bold;
			height: 100%;
		}

		.picker {
			// background-color: red;
			min-width: 60px;
			max-width: 60px;
			height: 50px;
			margin-left: 10px;
			border: #EEE 2px solid;

			.upload-view {
				background-color: WhiteSmoke;
				// width: 70px;
				min-width: 50px;
				min-height: 50px;
				height: 50px;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;


				.text {
					font-size: 10px;
				}

			}
		}
	}





	.page-body {
		height: calc(100vh - var(--top-nav-bar) - var(--status-bar-height));
	}



	.cover-image {
		width: 90px;
		height: 50px;
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
<template>
	<view class="">
		<view class="background">
		</view>
		<!-- 选择文件上传方式 -->
		<u-action-sheet :show="fileChoice" :actions="selectList" title="文件在哪？" description="文件在哪里呀文件在哪里" cancelText="取消"
			safeAreaInsetBottom="true" @select="handleSelect($event)" @close="fileChoice = false">
		</u-action-sheet>
		<!-- 确认上传按钮 -->
		<u-modal :content="modalContent" :show="modalShow" showCancelButton closeOnClickOverlay @confirm="uploadData()"
			@cancel="modalShow = false" @close="modalShow = false"></u-modal>
		<!-- 上传时的加载页面 -->
		<u-loading-page bgColor="rgba(0, 0, 0, 0.3)" fontSize="25" iconSize="40" loadingColor="#ddd" color="#eee"
			:loadingText="loadingText" loading-mode="spinner" :loading="loading">
		</u-loading-page>
		<!-- 上传成功或失败提示消息 -->
		<u-notify ref="uNotify"></u-notify>
		<view class="content">
			<view class="upload-text">
				{{ uploadText }}
			</view>
			<u-gap height="10"></u-gap>
			<!-- 文件上传 -->
			<view class="upload" @click="fileChoice = true">
				<view class="box">
					<image mode="widthFix" class="upload-img" src="/static/uploads/upload.png" alt="点击上传"></image>
					<view class="upload-title">
						点击上传
					</view>
				</view>
				<u-gap height="20"></u-gap>
			</view>
			<u-gap height="20"></u-gap>
			<uploadsHistory @loading='toLoading($event)' @error="notify('error', $event)"
				@success="notify('success', $event)" :data="data">
			</uploadsHistory>
		</view>

	</view>
</template>

<script>
	import uploadsHistory from '@/components/endTermBUploadHistory/endTermBUploadHistory.vue';
	export default {
		components: {
			uploadsHistory,
		},
		data() {
			return {
				// 文件上传的两个选项
				selectList: [{
						id: 1,
						name: '微信聊天文件上传',
						color: 'black',
						fontSize: '20',
						disabled: true
					},
					{
						id: 2,
						name: '本地文件上传',
						color: 'black',
						fontSize: '20',
					},
					{
						id: 3,
						name: '百度网盘上传(暂未开发)',
						color: 'black',
						fontSize: '20',
						disabled: true,
					},

				],
				file: '', // 文件路径
				fileChoice: false, // 文件选择菜单
				uploadText: '！请上传《社会实践活动结项报告》以及社会实践成果材料,只能上传单个文件,如有多文件请打包上传', // 上传提示
				modalContent: '确认上传吗，上传后不可修改哟',
				modalShow: false,
				loadingText: '上传中,请勿退出', // 上传时文字
				loading: false, //是否加载中
				// 返回的历史上传数据
				data: [],
			}
		},
		onLoad() {
			// 获取历史记录
			this.getData();

		},
		methods: {
			// 获取历史
			getData() {
				uni.showLoading({
					title: '正在加载'
				})
				const PATH = '/user/files/end';
				uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						uni.hideLoading();

						console.log(res);
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						this.data = res.data.data;
					},
					fail: (error) => {
						uni.hideLoading();

						console.log(error);
						this.notify('error', '网络异常,请稍后重试');
					}
				})
			},
			// 点击了选项触发
			handleSelect(obj) {
				console.log(obj);
				// obj 传递被选择了的对象
				// id = 1,聊天文件上传 2，本地上传。3，百度网盘上传
				switch (obj.id) {
					case 1:
						wx.chooseMessageFile({
							count: 1,
							type: 'file',
							extension: ['.zip', '.rar', '.7z', '.tar', 'gz', '.tgz'],
							success: (res) => {
								console.log(res);
								this.file = res.tempFiles[0].path;
								this.modalShow = true;
							},
							fail: (error) => {
								console.log(error);
							}
						})
						break;
					case 2:
						uni.chooseFile({
							count: 1,
							type: 'all',
							extension: ['.zip', '.rar', '.7z', '.tar', 'gz', '.tgz'],
							success: (res) => {
								console.log(res);
								this.file = res.tempFiles[0].path;
								this.modalShow = true;
							},
							fail: (error) => {
								console.log(error);
							}
						})
						break;
					case 3:
						break;
				}
			},
			// 上传数据
			uploadData() {
				const PATH = '/user/files/end';
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
						this.loading = false;
						console.log(res);
						if (res.data.code === 0) {
							// 报错,返回报错信息
							this.notify('error', res.data.msg);
							return;
						}
						this.notify('success', '文件上传成功');
						this.getData();
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
			// 错误提示
			notify(type, message) {
				this.$refs.uNotify.show({
					top: 10,
					type,
					message,
					duration: 1000 * 3,
					fontSize: 20,
				})
			},
			// 是否加载中
			toLoading(condition) {
				console.log(condition);
				if (condition) {
					this.loading = true;
					return;
				}
				this.loading = false;
			}
		}
	}
</script>

<style lang="less" scoped>
	.background {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: -10;
		background-color: #efeded;
	}


	.content {
		width: 100%;
		margin: auto;
	}


	.upload {
		background-color: white;
	}

	// upload

	.upload-text {}

	.upload {
		.box {
			width: fit-content;
			margin: auto;
			display: flex;
			flex-direction: column;
			text-align: center;
			justify-content: center;
		}

		.upload-title {
			color: grey;
			font-size: 20px;
			letter-spacing: 1.5px;
		}
	}

	.upload-img {
		width: 120px;
	}
</style>
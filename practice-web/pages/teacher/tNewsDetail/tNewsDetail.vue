<template>
	<view class="container">
		<view class="background">
		</view>
		<u-notify ref="uNotify"></u-notify>
		<view>
			<!-- 输入框示例 -->
			<uni-popup ref="rejectReason" type="dialog">
				<uni-popup-dialog ref="inputClose" mode="input" v-model="rejectReason" title="驳回理由"
					placeholder="请输入驳回理由" @confirm="passOrReject(3)"></uni-popup-dialog>
			</uni-popup>
		</view>
		<view class="content">
			<u-gap height="20"></u-gap>
			<view class="title">
				{{ news.title }}
			</view>
			<u-gap height="8"></u-gap>
			<view class="time">
				{{ news.uploadTime }}
			</view>
			<u-gap height="5"></u-gap>
			<view class="text">
				<view class="ql-container">
					<rich-text class="ql-editor" :nodes="news.text">
					</rich-text>
				</view>
			</view>
		</view>
		<u-gap height="60"></u-gap>
		<view class="bottom">
			<view v-if="news.label === 1" @click="passOrReject(2)">通过</view>
			<view v-if="news.label === 1" @click="$refs.rejectReason.open()">驳回</view>
			<view v-if="news.label !== 1" :style="news.label === 2 ? 'color: green': 'color: red'">
				{{ news.label === 2 ? '已通过' : '已驳回' }}
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				newsId: 0,
				news: {},
				// 驳回理由
				rejectReason: '',
				// 显示modal

			}
		},
		onLoad(options) {
			console.log(options);
			this.newsId = +options.id;
			console.log(this.newsId);
			this.getData();
		},
		methods: {
			getData() {
				const path = '/news/tDetail';
				uni.request({
					url: `${getApp().globalData.URL}${path}?id=${this.newsId}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						console.log(res);
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						this.news = res.data.data;
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})
			},
			passOrReject(label) {
				const path = '/news/verify';

				uni.request({
					url: getApp().globalData.URL + path,
					method: 'POST',
					header: {
						'token': uni.getStorageSync('token'),
					},
					data: {
						id: this.newsId,
						label,
						rejectReason: this.rejectReason
					},
					success: (res) => {
						console.log(res);
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						this.notify('success', '审核完成，即将返回');
						setTimeout(() => {
							uni.navigateBack({
								delta: 1,
								success() {
									uni.$emit('update');
								}
							});
						}, 1000)
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
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

		}
	}
</script>

<style lang="less" scoped>
	@import url("/pages/student/news/editor.css");

	.container {}

	.background {}

	.content {
		width: 90%;
		margin: auto;


		.title {
			font-weight: bold;
			font-size: 17px;
		}

		.time {
			color: gray;
			font-size: 12px;
		}

		.text {}
	}

	.bottom {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		height: 50px;
		z-index: 10;
		background-color: white;
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		align-items: center;

		view {
			border-radius: 10px;
			width: 35%;
			height: 50%;
			background-color: rgb(238, 189, 161);
			text-align: center;
			text-indent: 14px;
			letter-spacing: 14px;
			padding: 5px 0;
			box-shadow: 1px 3px 2px rgba(0, 0, 0, 0.3);
		}
	}
</style>
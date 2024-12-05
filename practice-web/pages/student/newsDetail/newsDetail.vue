<template>
	<view class="container">

		<view class="background">

		</view>
		<u-notify ref="uNotify"></u-notify>

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
	</view>
</template>

<script>
	export default {
		data() {
			return {
				newsId: 0,
				news: {},
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
				const path = '/news/detail';
				uni.request({
					url: `${getApp().globalData.URL}${path}?id=${this.newsId}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						console.log(res);
						this.news = res.data.data;
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
	}
</style>
<template>
	<view class="container">

		<view class="background">
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
	</view>
</template>

<script>
	export default {
		data() {
			return {
				projectId: 0,
				news: {},
			}
		},
		onLoad(options) {
			console.log(options);
			this.projectId = options.id;
			console.log(this.projectId);
			this.getData();
		},
		methods: {
			getData() {
				const path = '/news/detail';
				uni.request({
					url: `${getApp().globalData.URL}${path}?projectId=${this.projectId}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						console.log(res);
						this.news = res.data.data;
					},
					fail: (error) => {
						console.log(error);
					}
				})
			}
		}
	}
</script>

<style lang="less" scoped>
	@import url("/pages/news/editor.css");

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
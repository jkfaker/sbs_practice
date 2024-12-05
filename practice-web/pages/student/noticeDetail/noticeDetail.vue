<template>
	<view class="container">

		<view class="background">
		</view>
		<u-notify ref="uNotify"></u-notify>

		<view class="content">
			<u-gap height="20"></u-gap>
			<view class="title">
				{{ data.title }}
			</view>
			<u-gap height="8"></u-gap>
			<view class="time">
				{{ data.updateTime }}
			</view>
			<u-gap height="5"></u-gap>
			<view class="text">
				<view class="ql-container">
					<rich-text class="ql-editor" :nodes="data.text">
					</rich-text>
				</view>
			</view>
			<u-gap height="20"></u-gap>
			<!-- notice 的附件 -->
			<uni-link v-if='data.fileName' color="green" :href="fileUrl(data)" :text="'附件:' + data.fileName"></uni-link>
			<u-gap height="40"></u-gap>

		</view>
	</view>
</template>

<script>
	import uniLink from '@/uni_modules/uni-link/components/uni-link/uni-link.vue';
	export default {
		components: {
			uniLink,
		},
		data() {
			return {
				// 通告id
				id: 0,
				data: {}
			}
		},
		onLoad(options) {
			console.log(options);
			this.id = +options.id;
			this.getData();
		},

		methods: {
			// 单个文件下载链接
			fileUrl(item) {
				return '/practice' + getApp().globalData.teacherFilePath + item.fileName;
			},
			getData() {
				const PATH = '/notice/detail';
				uni.request({
					url: `${getApp().globalData.URL}${PATH}?id=${this.id}`,
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
						this.data = res.data.data;
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

	.attach {
		color: green;
		font-size: 14px;
		text-decoration: underline;
	}
</style>
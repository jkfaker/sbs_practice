<template>
	<view class="container">
		<view class="background">
		</view>
		<u-notify ref="uNotify"></u-notify>
		<view class="content">
			<u-gap height="10"></u-gap>
			<u-gap height="12"></u-gap>
			<u-empty v-if="!list.length" mode="data" icon="/static/uploads/empty.png">
			</u-empty>
			<view v-if="list.length" class="list" v-for="(item,index) in list" :key="index">
				<view class="item">
					<image @click="turnTo(item)" class="img" mode="widthFix"
						:src="'/static/uploads/images/' + item.coverImage">
					</image>
					<view class="reason" :style="`background-color: ${ getLabelAndColor('color', item) }`">
						{{ getLabelAndColor('text', item) }}
						<text class="text" v-if="item.label === 3">
							。理由：{{ item.rejectReason }}
						</text>
					</view>
					<view class="textBar">
						<view class="title">
							<scrollVue :text="item.title"></scrollVue>
						</view>
						<view v-if="item.label !== 3" class="leader">
							负责人：{{ item.leaderName }}
						</view>
						<view @click="handleReEdit(item)" v-if="item.label === 3"
							style="color: greenyellow;text-decoration: underline; " class="leader">
							点我重新编辑
						</view>
					</view>

				</view>
				<u-gap height="20"></u-gap>
			</view>
		</view>
		<view class="add" @click="addNews()">
			+
		</view>
	</view>
</template>

<script>
	import scrollVue from '@/components/scroll/scroll.vue';
	export default {
		components: {
			scrollVue
		},
		data() {
			return {
				// 分页查询获取的后端结果
				page: {},
				list: [
					// {
					// 	projectId: 1,
					// 	coverImage: '',
					// 	title: '谈杏花楼故事，养老只好精神',
					// 	leaderName: '刘奇铎',
					// },
					// {
					// 	projectId: 2,
					// 	coverImage: '',
					// 	title: '谈杏花楼故事，养老只好精神',
					// 	leaderName: '刘奇铎',
					// },
					// {
					// 	projectId: 3,
					// 	coverImage: '',
					// 	title: '谈杏花楼故事，养老只好精神',
					// 	leaderName: '刘奇铎',
					// },
				],
				// 是否允许上传新闻稿 1 允许，0不允许
				allow: 1,
				status: 'more',
			}
		},
		async onShow() {
			await this.isSchoolJudge();
			await this.getData();
		},

		async onPullDownRefresh() {
			await this.getData();
			uni.stopPullDownRefresh();
		},
		methods: {
			// 判断是否为校级立项
			async isSchoolJudge() {
				const PATH = '/user/news/judge';
				const res = await uni.request({
					url: `${getApp().globalData.URL}${PATH}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
				})
				if (res.data.code === 0) {
					this.allow = 0;
					this.notify('error', res.data.msg);
					return;
				}
				console.log(res);
			},
			handleReEdit(item) {
				uni.setStorageSync('cropImageName', item.coverImage);
				uni.setStorageSync('title', item.title);
				uni.setStorageSync('text', item.text);
				console.log("item.text" + item.text);
				uni.navigateTo({
					url: getApp().globalData.pagePath.newsEditor
				})
			},
			// 处理上面的背景颜色和标题
			getLabelAndColor(type, item) {
				if (type === 'text') {
					switch (item.label) {
						case 1:
							return '待审核';
						case 2:
							return '已通过';
						case 3:
							return '已驳回';
					}
				} else if (type === 'color') {
					switch (item.label) {
						case 1:
							return 'rgba(0, 0, 200, 0.5)';
						case 2:
							return 'rgba(0, 198, 0, 0.6)';
						case 3:
							return 'rgba(200, 0, 0, 0.5)';
					}
				}

			},
			async getData() {
				uni.showLoading({
					title: '正在加载'
				})
				const PATH = '/user/news/mine';
				const res = await uni.request({
					url: `${getApp().globalData.URL}${PATH}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
				})
				uni.hideLoading();
				if (res.data.code === 0) {
					this.allow = 0;
					this.notify('error', res.data.msg);
					return;
				}
				console.log(res);
				this.list = res.data.data;

			},
			addNews() {
				if (this.allow === 1) {
					uni.navigateTo({
						url: getApp().globalData.pagePath.newsEditor,
					})
				} else {
					this.notify('error', '权限不足！');
					return;
				}

			},
			turnTo(item) {
				const id = item.id;
				uni.navigateTo({
					url: `${getApp().globalData.pagePath.newsDetail}?id=${id}`,
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
	.background {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: -1;
		background-image: linear-gradient(to bottom, #ffe0dd, #fdecdc, #fcf9da);
	}

	.list {
		width: 90%;
		margin: auto;

		.item {
			border-radius: 10px;
			background-color: white;
			width: 100%;
			overflow: hidden;
			position: relative;

			.img {
				width: 100%;
				display: block;
			}

			.reason {
				position: absolute;
				top: 0;
				min-height: 30px;
				padding-left: 20px;
				padding-top: 10px;
				width: 100%;
				color: white;
			}

			.textBar {
				position: absolute;
				bottom: 0;
				height: 50px;
				background-color: rgba(50, 50, 50, 0.4);
				width: 100%;
				z-index: 100;

				// .title {
				// 	margin-left: 10px;
				// 	line-height: 30px;
				// 	font-size: 18px;
				// 	font-weight: 400;
				// 	color: white;
				// }

				.leader {
					text-align: right;
					width: 92%;
					margin: auto;
					font-size: 14px;
					color: white;
				}
			}
		}

	}

	.add {
		position: absolute;
		right: 20px;
		bottom: 20px;
		background-color: orange;
		width: 50px;
		height: 50px;
		border-radius: 50%;
		font-size: 35px;
		color: white;
		text-align: center;
		vertical-align: middle;
		cursor: pointer;
		box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3), -2px -2px 5px rgba(255, 255, 255, 0.5);
		transition: box-shadow 0.3s ease;
	}
</style>
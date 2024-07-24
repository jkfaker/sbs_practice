<template>
	<view class="container">
		<view class="background">
		</view>
		<view class="content">
			<u-gap height="10"></u-gap>
			<uni-search-bar style="width: 90%;margin: auto;" color="#FFFFFF" radius="10" placeholder="请输入标题或负责人姓名"
				v-model="searchText"></uni-search-bar>
			<u-gap height="12"></u-gap>
			<u-empty v-if="!page.list" mode="data" icon="/static/uploads/empty.png">
			</u-empty>
			<view class="list" v-for="(item,index) in page.list" :key="index">
				<view class="item" @click="turnTo(item)">
					<image class="img" mode="widthFix" :src="'@/static/uploads/images/' + item.coverImage">
					</image>
					<view class="textBar">
						<view class="title">
							{{ item.title }}
						</view>
						<view class="leader">
							负责人：{{ item.leaderName }}
						</view>
					</view>
				</view>
				<u-gap height="20"></u-gap>
			</view>
		</view>
		<uni-load-more v-show="page.list" :status="status"></uni-load-more>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 搜索框输入值
				searchText: '',
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
				pageNo: 1,
				allPage: 1,
				pageSize: 10,
				status: 'more',
			}
		},
		onLoad() {
			uni.startPullDownRefresh();
		},
		async onReachBottom() {
			if (this.pageNo < this.allPage) {
				this.pageNo++;
				this.status = 'loading'; //加载中状态
				await this.getData()
			} else {
				this.status = 'noMore'; //加载完状态
			}
		},
		onPullDownRefresh() {
			this.page = {};
			this.pageNo = 1;
			this.searchText = '';
			//调用获取数据方法
			this.getData();
			setTimeout(() => {
				//结束下拉刷新
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			async getData() {
				const path = '/news/shows';
				await uni.request({
					url: `${getApp().globalData.URL}${path}?pageNo=${this.pageNo}&pageSize=${this.pageSize}&condition=${this.searchText}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						console.log(res);
						this.allPage = res.data.data.pages;
						if (this.pageNo === 1) {
							this.page = res.data.data;
						} else {
							this.page.list.push(...res.data.data.list);
						}
					},
					fail: (error) => {
						console.log(error);
					}
				})
			},
			turnTo(item) {
				const id = item.projectId;
				uni.navigateTo({
					url: `/pages/newsDetail/newsDetail?id=${id}`,
				})
			},
		},
		watch: {
			// 处理搜索框的搜索功能
			async searchText(value) {
				clearTimeout(this.timer);
				this.timer = setTimeout(async () => {
					this.page = {};
					await this.getData();
				}, 1000)

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

			.textBar {
				position: absolute;
				bottom: 0;
				height: 50px;
				background-color: rgba(50, 50, 50, 0.4);
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				width: 100%;

				.title {
					margin-left: 10px;
					line-height: 40px;
					font-size: 18px;
					font-weight: 400;
					color: white;
				}

				.leader {
					line-height: 70px;
					font-size: 10px;
					color: grey;
					margin-right: 10px;
					color: white;
				}
			}
		}

	}
</style>
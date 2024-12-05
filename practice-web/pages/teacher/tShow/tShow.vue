<template>
	<view class="container">
		<view class="background">
		</view>
		<u-notify ref="uNotify"></u-notify>
		<view class="content">
			<u-gap height="10"></u-gap>
			<uni-search-bar style="width: 90%;margin: auto;" color="#FFFFFF" radius="10" placeholder="请输入标题或负责人姓名"
				v-model="searchText"></uni-search-bar>
			<u-gap height="12"></u-gap>
			<u-empty v-if="!page.list" mode="data" icon="/static/uploads/empty.png">
			</u-empty>
			<view class="list" v-for="(item,index) in page.list" :key="index">
				<view class="item" @click="turnTo(item)">
					<image class="img" mode="widthFix" :src="'/static/uploads/images/' + item.coverImage">
					</image>
					<view class="textBar">
						<view class="title">
							<scrollVue :text="item.title"></scrollVue>
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
		<tabbar :index="pageNo"></tabbar>
	</view>
</template>

<script>
	import tabbar from '@/components/teacherATabbar/teacherATabbar.vue';
	import scrollVue from '@/components/scroll/scroll.vue';
	export default {
		components: {
			tabbar,
			scrollVue
		},
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
			this.page = {};
			this.pageNo = 1;
			this.searchText = '';
			this.getData();
		},
		async onReachBottom() {
			if (this.pageNo < this.allPage) {
				this.pageNo++;
				this.status = 'loading'; //加载中状态
				await this.getData();
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
			async getData() {
				const path = '/news/shows';
				const res = await uni.request({
					url: `${getApp().globalData.URL}${path}?pageNo=${this.pageNo}&pageSize=${this.pageSize}&condition=${this.searchText}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
				})
				if (res.data.code === 0) {
					this.notify('error', res.data.msg);
					return;
				}
				console.log(res);
				this.allPage = res.data.data.pages;
				if (this.pageNo === 1) {
					this.page = res.data.data;
				} else {
					this.page.list.push(...res.data.data.list);
				}
			},
			turnTo(item) {
				const id = item.id;
				uni.navigateTo({
					url: `${getApp().globalData.pagePath.newsDetail}?id=${id}`,
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
				width: 100%;

				.title {
					margin-left: 10px;
					line-height: 30px;
					font-size: 18px;
					font-weight: 400;
					color: white;
				}

				.leader {
					text-align: right;
					width: 92%;
					margin: auto;
					font-size: 10px;
					color: white;
				}
			}
		}

	}
</style>
<template>
	<view class="container">
		<view class="background">
		</view>
		<u-notify ref="uNotify"></u-notify>
		<uni-segmented-control @clickItem="clickSegment($event)" :values="items" styleType="text"
			activeColor="#dd524d"></uni-segmented-control>
		<view class="content">
			<u-gap height="10"></u-gap>
			<u-gap height="12"></u-gap>
			<u-empty v-if="!page || !page.list.length" mode="data" icon="/static/uploads/empty.png">
			</u-empty>
			<view v-if="page && page.list.length" class="list" v-for="(item,index) in page.list" :key="index">
				<view class="item" @click="turnTo(item)">
					<image class="img" mode="widthFix" :src="'/static/uploads/images/' + item.coverImage">
					</image>
					<view class="reason" v-if="item.label === 3">
						<view class="text">
							驳回理由： {{ item.rejectReason }}
						</view>
					</view>
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
		<uni-load-more v-show="page && page.list.length" :status="status"></uni-load-more>
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
				// 搜索框输入值
				searchText: '',
				// 分页查询获取的后端结果
				page: {},
				items: [
					'待审核',
					'已审批',
					'已驳回'
				],
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
				pageSize: 5,
				status: 'more',
				// news标签 1:待审核。 2， 已审批， 3， 已驳回
				label: 1,
			}
		},
		onLoad() {
			this.page = {};
			this.pageNo = 1;
			//调用获取数据方法
			this.getData();
		},
		mounted() {
			// uni.$on('update', () => {
			// 	this.page = {};
			// 	this.pageNo = 1;
			// 	//调用获取数据方法
			// 	this.getData();
			// 	console.log("update");
			// });
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

			//调用获取数据方法
			this.getData();
			setTimeout(() => {
				//结束下拉刷新
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			async getData() {
				const PATH = '/teacher/news/verify/shows';
				const res = await uni.request({
					url: `${getApp().globalData.URL}${PATH}?pageNo=${this.pageNo}&pageSize=${this.pageSize}&newsLabel=${this.label}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
				})
				console.log(res);
				if (res.data.code === 0) {
					this.notify('error', res.data.msg);
					return;
				}
				this.allPage = res.data.data.pages;
				console.log(this.pageNo);
				if (this.pageNo === 1) {
					this.page = res.data.data;
				} else {
					this.page.list.push(...res.data.data.list);
				}
				console.log(this.page);
			},
			// 点击分段器
			clickSegment(e) {
				this.label = e.currentIndex + 1;
				this.getData();
			},
			turnTo(item) {
				const id = item.id;
				uni.navigateTo({
					url: `${getApp().globalData.pagePath.tNewsDetail}?id=${id}`,
				})
			},
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
			position: relative;
			border-radius: 10px;
			background-color: white;
			width: 100%;
			overflow: hidden;


			.img {
				width: 100%;
				display: block;
			}

			.reason {
				position: absolute;
				top: 0;
				min-height: 30px;
				background-color: rgba(157, 59, 20, 0.7);
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
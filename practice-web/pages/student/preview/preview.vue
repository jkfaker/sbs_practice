<template>
	<view class="container">
		<view class="background">
		</view>
		<u-notify ref="uNotify"></u-notify>

		<view class="box-bg">
			<uniNavBar height='44' :fixed="true" @clickLeft="handleReturn()" @clickRight="handleNext()" :border="false"
				shadow left-icon="left" leftText="返回" rightText="发布" title="新闻稿预览">
			</uniNavBar>
		</view>
		<view class="content">
			<u-gap height="20"></u-gap>
			<view class="title">
				{{ news.title }}
			</view>
			<u-gap height="8"></u-gap>
			<view class="time">
				{{ uploadTime }}
			</view>
			<u-gap height="5"></u-gap>
			<view class="text">
				<view class="ql-container">
					<rich-text class="ql-editor" :nodes="news.text">
					</rich-text>
				</view>
			</view>
			<u-gap height="100"></u-gap>
		</view>
		<u-sticky>
			<view class="section">
				<uni-section class="mb-10" titleFontSize="17px" title="封面预览" type="line"></uni-section>
			</view>
		</u-sticky>
		<u-gap height="50px"></u-gap>
		<view class="cover">
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
	</view>
</template>

<script>
	import scrollVue from '@/components/scroll/scroll.vue';
	import uniNavBar from '@/uni_modules/uni-nav-bar/components/uni-nav-bar/uni-nav-bar.vue';
	export default {
		components: {
			uniNavBar,
			scrollVue
		},
		data() {
			return {
				projectId: 0,
				uploadTime: '',
				news: {
					title: '',
					text: '',
					coverImage: '',
				},
				page: {
					list: [{
							coverImage: 'news1.png',
							title: '探杏花楼故事，扬老字号精神',
							leaderName: '张三',
						},
						{
							coverImage: '',
							title: '',
							leaderName: '',
						},
						{
							coverImage: 'news2.png',
							title: '探杏花楼故事，扬老字号精神',
							leaderName: '李四',
						},
						{
							coverImage: 'news3.png',
							title: '探杏花楼故事，扬老字号精神',
							leaderName: '王五',
						},
					]
				},
			}
		},
		onLoad() {
			this.getData();
		},
		methods: {
			getData() {

				this.news.title = uni.getStorageSync('title');
				this.uploadTime = this.updateCurrentTime();
				this.news.text = uni.getStorageSync('text');
				this.news.coverImage = uni.getStorageSync('cropImageName');
				this.page.list[1].coverImage = this.news.coverImage;
				this.page.list[1].title = this.news.title;
				this.page.list[1].leaderName = '你的名字';
			},
			// 点击发布触发
			async handleNext() {
				// 判断新闻稿是否已经存在了，如果存在，则更新
				/* const judgePath = '/news/judge';
				const res = await uni.request({
					url: getApp().globalData.URL + judgePath,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},

				});
				console.log(res);
				if (res.data.data != 0)
					this.news.id = res.data.data; */
				const PATH = '/user/news/upload';
				uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'POST',
					data: this.news,
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						const id = res.data.data;
						console.log(res);
						// 跳转到新闻稿提交完成界面
						uni.redirectTo({
							url: getApp().globalData.pagePath.newsFinish + '?id=' + id,
						})
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})

			},
			// 点击返回触发
			handleReturn() {
				uni.navigateBack({
					delta: 1,
				})
			},

			// 获取当前时间
			updateCurrentTime() {
				const now = new Date();
				const year = now.getFullYear();
				const month = ('0' + (now.getMonth() + 1)).slice(-2);
				const day = ('0' + now.getDate()).slice(-2);
				const hours = ('0' + now.getHours()).slice(-2);
				const minutes = ('0' + now.getMinutes()).slice(-2);
				const seconds = ('0' + now.getSeconds()).slice(-2);
				return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
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

	.container {
		--header-nav-bar: 44px;
		--section-height: 20px;
	}

	.background {}

	.content {
		min-height: calc(100vh - var(--header-nav-bar) - var(--section-height));
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

	.section {
		height: var(--section-height);
	}

	.cover {
		height: calc(100vh - var(--section-height));
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
				// display: flex;
				// flex-direction: row;
				// justify-content: space-between;
				width: 100%;

				.title {
					width: 100%;
				}

				.leader {
					text-align: right;
					width: 92%;
					margin: auto;
					font-size: 14px;
					color: white;
				}

				/* .leader {
					line-height: 70px;
					font-size: 10px;
					color: grey;
					margin-right: 10px;
					color: white;
				} */
			}
		}

	}
</style>
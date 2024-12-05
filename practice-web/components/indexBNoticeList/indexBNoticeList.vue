<template>
	<view class="container">
		<view class="content">
			<!-- 标题 -->
			<view class="header">
				<view class="title">
					<image class="img" src="@/static/icon/message.png"></image>
					<view class="text"> {{ title }} </view>
				</view>
			</view>


			<!--  列表 -->
			<view class="content">
				<u-gap height="20"></u-gap>
				<!--  empty  -->
				<view v-if="!list.length" class="">
					<u-empty mode="data" textSize="20" height="300" width="300" icon="/static/icon/empty.png">
					</u-empty>
				</view>
				<view v-if="list.length" class="list" v-for="(item, index) in list" :key='index'>
					<view class="item">
						<view @click="handleClickNotice(item)" class="">
							<view class="title">
								{{ item.title }}
							</view>
							<view class="text">
								{{ item.text }}...
							</view>
							<view class="time">
								发布时间：{{ item.createTime }}
							</view>
						</view>
					</view>
					<u-gap height="15"></u-gap>
				</view>
			</view>


		</view>
	</view>
</template>

<script>
	export default {
		name: "indexBNoticeList",
		data() {
			return {
				title: '服务资讯',
				list: [],
			};
		},
		created() {
			const PATH = '/notice/getAll';
			uni.request({
				url: getApp().globalData.URL + PATH,
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
					this.list = res.data.data;
				},
				fail: (error) => {
					console.log(error);
					this.notify('error', '网络异常！');
				}
			})
		},
		methods: {
			// 点击通告查看通告详情
			handleClickNotice(item) {
				uni.navigateTo({
					url: getApp().globalData.pagePath.noticeDetail + '?id=' + item.id,
				})
			},
		}
	}
</script>

<style lang="less" scoped>
	.header {
		.title {
			display: flex;
			flex-direction: row;
			justify-content: start;
			align-items: start;


			.text {
				font-size: 16px;
			}
		}

		.img {
			height: 22px;
			width: 34px;
			margin-right: 10px;
		}
	}

	.list {
		width: 100%;
		margin: auto;
	}

	.item {
		background-color: white;
		padding: 10px;
		border-radius: 10px;

		.title,
		.text,
		.time,
		{
		margin-bottom: 10px;
	}

	.title {
		color: orange;
	}

	.text {
		color: black;
		font-size: 14px;
	}

	.time {
		color: grey;
		font-size: 10px;
	}


	}
</style>
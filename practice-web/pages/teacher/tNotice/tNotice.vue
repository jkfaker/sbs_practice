<template>
	<view class="container">
		<view class="background">
		</view>
		<u-notify ref="uNotify"></u-notify>
		<!-- 删除按钮 -->
		<u-modal :content="modalContent" :show="modalShow" showCancelButton closeOnClickOverlay @confirm="deleteById()"
			@cancel="modalShow = false" @close="modalShow = false"></u-modal>
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
					<view @click="(deleteId = item.id) && (modalShow = true)" class="delete">
						删除
					</view>
				</view>
				<u-gap height="15"></u-gap>
			</view>
		</view>
		<view class="add" @click="addNotice()">
			+
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: [
					/* {
					title: '关于2024年暑期社会实践活动通知',
					text: '关于2024年暑期社会实践活动通知关于2024年暑期社会实践活动通知',
					uploadTime: '2024-06-20',
				}, {
					title: '关于2024年暑期社会实践活动通知',
					text: '关于2024年暑期社会实践活动通知关于2024年暑期社会实践活动通知',
					uploadTime: '2024-06-20',
				}, */
				],
				deleteId: 0,
				modalShow: false,
				modalContent: "确定删除这条通告吗？"
			}
		},
		onLoad() {
			this.getData();
		},
		methods: {
			// 点击通告查看通告详情
			handleClickNotice(item) {
				uni.navigateTo({
					url: getApp().globalData.pagePath.noticeDetail + '?id=' + item.id,
				})
			},
			// 点击添加按钮
			addNotice() {
				uni.navigateTo({
					url: getApp().globalData.pagePath.tNoticeEditor
				})
			},
			// 点击删除按钮。
			deleteById() {
				const PATH = '/notice/delete';
				this.modalShow = false;
				uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'POST',
					header: {
						'token': uni.getStorageSync('token'),
					},
					data: {
						id: this.deleteId,
					},
					success: (res) => {
						console.log(res);
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}

						this.getData();
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})
			},
			// 获取notice数据
			getData() {
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
		background-color: #efeded;
	}

	.list {
		width: 90%;
		margin: auto;
	}

	.item {
		background-color: white;
		padding: 10px;
		border-radius: 10px;

		.title,
		.text,
		.time,
		.delete {
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

		.delete {
			color: red;
			font-size: 14px;
			text-decoration: underline;
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
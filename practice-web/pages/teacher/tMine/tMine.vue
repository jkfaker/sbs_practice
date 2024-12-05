<template>
	<view class="container">
		<view class="background"></view>
		<u-notify ref="uNotify"></u-notify>
		<u-toast ref="stay"></u-toast>
		<u-modal :show="showToast" closeOnClickOverlay="true" @close="showToast = false" @confirm="showToast = false"
			:title="title">
			<view>
				<!-- 小程序使用反馈 -->
				<image v-if="index === 4" src="@/static/uploads/mine.png" mode="widthFix" lazy-load="true"></image>
				<image v-if="index === 4" src="@/static/uploads/lu.jpg" mode="widthFix" lazy-load="true"></image>
				<text v-if="index === 4" class="little-text">
					@Author: 计科221 刘奇铎 \n @Designer：大数据222 陈含露
				</text>
			</view>
		</u-modal>
		<view class="content">
			<u-gap height="70"></u-gap>
			<userPicture :leader="leader"></userPicture>
			<u-gap height="40"></u-gap>
			<u-gap height="20"></u-gap>
			<settingList @handleClick="toast($event)"></settingList>
			<u-gap height="30"></u-gap>
		</view>

		<tabbar :index="pageNo"></tabbar>
	</view>

</template>

<script>
	import userPicture from '@/components/mineAUserPicture/mineAUserPicture.vue';
	import settingList from '@/components/mineCSettingList/mineCSettingList.vue';
	import tabbar from '@/components/teacherATabbar/teacherATabbar.vue';
	export default {
		components: {
			userPicture,
			tabbar,
			settingList,
		},
		data() {
			return {
				pageNo: 2,
				showToast: false,
				title: '',
				index: 0,
				// 我的成员信息
				members: [],
				// 我的信息
				leader: {},

			}
		},
		onLoad() {
			this.getLeader();
			this.getMembers();
		},
		methods: {
			getLeader() {
				const path = '/leader';
				uni.request({
					url: getApp().globalData.URL + path,
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
						this.leader = res.data.data;
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})
			},
			toast(index) {
				console.log(index);
				this.index = index;
				switch (index) {
					case 0:
						uni.navigateTo({
							url: getApp().globalData.pagePath.tManage,
						})
						break;
					case 1:
						uni.navigateTo({
							url: getApp().globalData.pagePath.tSubject,
						})
						break;
					case 3:
						uni.navigateTo({
							url: getApp().globalData.pagePath.tDate,
						})
						break;
					case 2:
					case 4:
						this.title = '小程序使用反馈';
						this.showToast = true;
						break;
				}

			},
			stay(type, message) {
				console.log(message);
				console.log(type);
				this.$refs.stay.show({
					duration: 2000,
					message,
					type,
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
		background-image: linear-gradient(to bottom, #FEC7AB, #FCEAC9, #FBFDDA, #FBFDDA);
	}

	.content {
		width: 90%;
		margin: auto;
	}

	.little-text {
		color: grey;
		font-size: 12px;
	}
</style>
<template>
	<view class="container">
		<view class="background"></view>
		<u-toast ref="stay"></u-toast>
		<u-notify ref="uNotify"></u-notify>
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
		<view class="container">
			<view class="content">
				<u-gap height="70"></u-gap>
				<userPicture :leader="leader"></userPicture>
				<u-gap height="40"></u-gap>
				<fourBoxes :members="members"></fourBoxes>
				<u-gap height="20"></u-gap>
				<settingList @handleClick="toast($event)"></settingList>
				<u-gap height="30"></u-gap>
				<!-- <logOut @handleClick="stay('success', '进来了就不要走了哦~~')"></logOut> -->
			</view>
		</view>

	</view>

</template>

<script>
	import userPicture from '@/components/mineAUserPicture/mineAUserPicture.vue';
	import fourBoxes from '@/components/mineBFourBoxes/mineBFourBoxes.vue';
	import settingList from '@/components/mineCSettingList/mineCSettingList.vue';
	export default {
		components: {
			userPicture,
			fourBoxes,
			settingList,
		},
		data() {
			return {
				showToast: false,
				title: '',
				index: 0,
				// 我的成员信息
				members: [],
				// 我的信息
				leader: {}
			}
		},
		onShow() {
			this.getLeader();
			this.getMembers();
		},
		methods: {
			getLeader() {
				const PATH = '/user/leader';
				uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						if (res.data.code == 0) {
							this.notify('error', res.data.msg);
							return;
						}
						console.log(res);
						this.leader = res.data.data;
					},
					fail: (error) => {
						this.notify('error', '网络异常！');
						console.log(error)
					}
				})
			},
			getMembers() {
				const PATH = '/user/member';
				uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						console.log(res);
						this.members = res.data.data.slice(1);
						console.log(this.members);
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！')
					}

				})
			},
			toast(index) {
				console.log(index);
				this.index = index;
				switch (index) {
					case 0:
						uni.navigateTo({
							url: getApp().globalData.pagePath.setup,
						})
						break;
					case 1:
						uni.navigateTo({
							url: getApp().globalData.pagePath.date,
						})
						break;
					case 2:
						uni.navigateTo({
							url: getApp().globalData.pagePath.endTerm,
						})
						break;
					case 3:
						uni.navigateTo({
							url: getApp().globalData.pagePath.news,
						})
						break;
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
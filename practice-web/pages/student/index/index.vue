<template>
	<view class="">
		<view class="background">
		</view>
		<u-notify ref="uNotify"></u-notify>
		<view class="content">
			<u-gap height="20"></u-gap>
			<!-- 轮播图 -->
			<view class="swiper">
				<u-swiper @click="swiperClick(index)" height="140" :circular="true" imgMode="widthFix" :autoplay="true"
					:list="swiperList" radius="10" bgColor="#ffffff"></u-swiper>
			</view>
			<u-gap height="20"></u-gap>
			<!-- 消息栏 -->
			<view class="notice">
				<u-notice-bar customStyle="border-radius: 10px" bgColor="white" fontSize="16" :text="noticeText"
					direction="column" @click="noticeClick()"></u-notice-bar>
			</view>
			<u-gap height="30"></u-gap>
			<!-- 五个按钮 左二右三 -->
			<fiveButtons></fiveButtons>
			<u-gap height="30"></u-gap>
			<noticeList></noticeList>
			<u-gap height="100"></u-gap>
		</view>
	</view>
</template>

<script>
	import fiveButtons from '@/components/indexAFiveButtons/indexAFiveButtons.vue';
	import noticeList from '@/components/indexBNoticeList/indexBNoticeList.vue';
	export default {
		components: {
			fiveButtons,
			noticeList
		},
		data() {
			return {
				swiperList: [
					'/static/uploads/swiper1.jpg',
				],
				noticeText: ['关于2024年暑期社会实践通知'],

			}
		},
		onLoad() {

		},
		methods: {
			swiperClick() {
				// TODO
				return;
			},
			noticeClick() {
				// TODO
				return;
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

<style lang="scss" scoped>
	.background {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: -1;
		background-color: #efeded;
	}


	.content {
		width: 90%;
		margin: auto;
	}
</style>
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
			<sevenButtons></sevenButtons>
			<u-gap height="30"></u-gap>
		</view>
		<tabbar :index="pageNo"></tabbar>
	</view>
</template>

<script>
	import sevenButtons from '@/components/tIndexASevenButtons/tIndexASevenButtons.vue';
	import tabbar from '@/components/teacherATabbar/teacherATabbar.vue';
	export default {
		components: {
			sevenButtons,
			tabbar
		},
		data() {
			return {
				pageNo: 0,
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
<template>
	<view class="container">
		<view class="list">
			<!-- <u-gap height="5"></u-gap> -->
			<view class="item" v-for="(item, index) in list" :key="index">
				<u-gap height="10"></u-gap>
				<view class="text" @click="$emit('handleClick', index)">
					<image mode="heightFix" :src="item.icon" class="icon"></image>
					<view>{{ item.text }}</view>
				</view>
				<u-gap height="5"></u-gap>
				<u-line color="white"></u-line>
			</view>
			<u-gap height="12"></u-gap>

		</view>
	</view>
</template>

<script>
	export default {
		name: "mineCSettingList",

		data() {
			return {
				list: [{
						icon: '/static/icon/myMessage.png',
						text: '我的消息',
						path: '/'
					}, {
						icon: '/static/icon/activity.png',
						text: '我的活动报名',
						path: '/'
					}, {
						icon: '/static/icon/record.png',
						text: '上传材料记录',
						path: '/'
					}, {
						icon: '/static/icon/date.png',
						text: '安全打卡记录',
						path: '/'
					},
					{
						icon: '/static/icon/us.png',
						text: '小程序使用反馈',
						path: '/'
					},
				]
			};
		},
	}
</script>

<style lang="scss" scoped>
	.container {
		background-color: #e5e5e5;
		border-radius: 10px;
		// border: 3px solid yellow;
		box-shadow: 2px 2px 5px grey;
	}

	.icon {
		margin-right: 10px;
		height: 17px;
	}

	.text {
		display: flex;
		flex-direction: row;
		justify-content: start;
		align-items: center;
		font-size: 15px;
	}

	.list {
		width: 90%;
		margin: auto;
	}
</style>
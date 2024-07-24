<template>
	<view class="container">
		<view class="content">
			<!-- 标题 -->
			<view class="title">
				<image class="img" src="@/static/icon/message.png"></image>
				<view class="text"> {{ title }} </view>
			</view>

			<!--  列表 -->
			<view class="list">
				<view class="listItem">
					<view class="box">

					</view>
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
			};
		}
	}
</script>

<style lang="less" scoped>
	.title {
		display: flex;
		flex-direction: row;
		justify-content: start;
		align-items: center;


		.text {
			font-size: 20px;
		}
	}

	.img {
		height: 30px;
		width: 40px;
		margin-right: 10px;
	}
</style>
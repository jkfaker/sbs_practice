<template>
	<view class="container">
		<view class="box">
			<view class="team">
				我的团队
			</view>
		</view>


		<view class="content my-yellow">
			<view v-if="!members.length" class="no-team">
				暂无团队，请先进行立项填报
			</view>
			<view class="item" v-if="members.length" v-for="(item,index) in members" :key="index">

				<image class="icon" :src="items[index].icon">
				</image>
				<u-gap height="10"></u-gap>
				<view class="text"> {{ item.memberName }}</view>
				<u-gap height="10"> </u-gap>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: "mineBFourBoxes",
		props: ['members'],
		data() {
			return {
				path: "/pages/myAppoint/myAppoint?id=",
				items: [{
						icon: "/static/uploads/member1_header.png",
						param: "0"
					},
					{
						icon: "/static/uploads/member2_header.png",
						param: "1"
					},
					{
						icon: "/static/uploads/member3_header.png",
						param: "2"
					},
					{
						icon: "/static/uploads/member4_header.png",
						param: "3"
					},
				]
			};
		},
		methods: {}
	}
</script>

<style lang="scss" scoped>
	.container {
		background-color: #e5e5e5;
		border-radius: 10px;
		box-shadow: 2px 2px 5px grey;
		min-height: 110px;
	}

	.box {
		width: 92%;
		margin: auto;

		.team {
			padding: 8px 0;
			color: darkorange;
			font-size: 14px;
			line-height: 14px;
			border-bottom: 1px solid green;
		}
	}

	.content {
		display: flex;

		.no-team {
			margin: auto;
			line-height: 90px;
		}
	}

	.item {
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		margin: 10px 5px 10px 30px;
	}

	.icon {
		width: 40px;
		height: 40px;
	}

	.text {
		font-size: 14px;
	}
</style>
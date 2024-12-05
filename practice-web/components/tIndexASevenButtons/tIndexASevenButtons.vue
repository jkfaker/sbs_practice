<template>
	<view class="">
		<u-row gutter="17">
			<u-col span="6">
				<view class="left">
					<view class="top margin" @click="linkTo('tSetup')">
						<view class="title">
							{{ list.tSetup }}
						</view>
					</view>
				</view>
			</u-col>
			<u-col span="6">
				<view class="right">
					<view class="top margin" @click="linkTo('tMidTerm')">
						<view class="title">
							{{ list.tMidTerm }}
						</view>
					</view>
					<u-gap height="10"></u-gap>
					<view class="middle margin" @click="linkTo('tEndTerm')">
						<view class="title">
							{{ list.tEndTerm }}
						</view>
					</view>
				</view>
			</u-col>
		</u-row>
		<u-gap height="10"></u-gap>
		<u-row gutter="17">
			<u-col span="6">
				<view class="left">
					<view class="bottom margin" @click="linkTo('tDate')">
						<view class="title">
							{{ list.tDate }}
						</view>
					</view>
				</view>
			</u-col>
			<u-col span="6">
				<view class="right">
					<view class="bottom margin" @click="linkTo('tNews')">
						<view class="title">
							{{ list.tNews }}
						</view>
					</view>
				</view>
			</u-col>
		</u-row>
		<u-gap height="10"></u-gap>
		<u-row gutter="17">
			<u-col span="6">
				<view class="left">
					<view class="end margin" @click="linkTo('tNotice')">
						<view class="title">
							{{ list.tNotice }}
						</view>
					</view>
				</view>
			</u-col>
			<u-col span="6">
				<view class="right">
					<view class="end margin" @click="linkTo('tReport')">
						<view class="title">
							{{ list.tReport }}
						</view>
					</view>
				</view>
			</u-col>
		</u-row>
	</view>
</template>

<script>
	export default {
		name: "tIndexAFiveButtons",
		data() {
			return {
				list: {
					tSetup: '院级校级立项审核',
					tMidTerm: '中期材料审核',
					tEndTerm: '结项材料审核',
					tDate: '安全打卡审核',
					tNews: '新闻稿审核',
					tNotice: '发送通告',
					tReport: '情况上报',
				}
			};
		},
		methods: {
			linkTo(page) {
				// 获取对应url
				const url = getApp().globalData.pagePath[page];
				uni.navigateTo({
					url,
				})
			}
		}
	}
</script>

<style lang="less" scoped>
	.title {
		font-size: 16px;
		width: 70px;
		text-align: center;
		margin-left: 20px;
	}

	.left {
		.top {
			height: 150px;
			background-image: url('@/static/uploads/btn_bgi1.png');
			background-repeat: no-repeat;
			background-size: contain;

			.title {
				padding: 15px 0;
			}
		}


		.bottom {
			background-image: url('@/static/uploads/btn_bgi2.png');

		}

		.end {
			background-image: url('@/static/uploads/btn_bgi6.png');
		}

		.bottom,
		.end {
			height: 70px;
			background-repeat: no-repeat;
			background-size: contain;
			display: flex;
			align-items: center;
			justify-content: start;

			.title {}
		}
	}

	.right {

		.title {}

		.top {
			margin-left: 0;
			height: 70px;
			background-image: url('@/static/uploads/btn_bgi3.png');
			background-repeat: no-repeat;
			background-size: contain;
			display: flex;
			align-items: center;
			justify-content: start;
		}

		.middle {
			height: 70px;
			background-image: url('@/static/uploads/btn_bgi4.png');
			background-repeat: no-repeat;
			background-size: contain;
			display: flex;
			align-items: center;
			justify-content: start;
		}

		.bottom {
			background-image: url('@/static/uploads/btn_bgi5.png');

		}

		.end {
			background-image: url('@/static/uploads/btn_bgi7.png');

		}

		.bottom,
		.end {
			height: 70px;
			background-repeat: no-repeat;
			background-size: contain;
			color: white;
			display: flex;
			align-items: center;
			justify-content: start;
		}
	}
</style>
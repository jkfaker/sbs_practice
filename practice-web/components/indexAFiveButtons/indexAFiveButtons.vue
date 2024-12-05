<template>
	<view class="">
		<u-row gutter="17">
			<u-col span="6">
				<view class="left">
					<view class="top margin" @click="linkTo('setup')">
						<view class="title">
							{{ list.setup }}
						</view>
					</view>
				</view>
			</u-col>
			<u-col span="6">
				<view class="right">
					<view class="top margin" @click="linkTo('midTerm')">
						<view class="title">
							{{ list.midTerm }}
						</view>
					</view>
					<u-gap height="10"></u-gap>
					<view class="middle margin" @click="linkTo('endTerm')">
						<view class="title">
							{{ list.endTerm }}
						</view>
					</view>
				</view>
			</u-col>
		</u-row>
		<u-gap height="10"></u-gap>
		<u-row gutter="17">
			<u-col span="6">
				<view class="left">
					<view class="bottom margin" @click="linkTo('date')">
						<view class="title">
							{{ list.date }}
						</view>
					</view>
				</view>

			</u-col>
			<u-col span="6">
				<view class="right">
					<view class="bottom margin" @click="linkTo('news')">
						<view class="title">
							{{ list.news }}
						</view>
					</view>
				</view>
			</u-col>
		</u-row>

	</view>
</template>

<script>
	export default {
		name: "indexAFiveButtons",
		data() {
			return {
				list: {
					setup: '立项填报',

					date: '安全打卡',

					midTerm: '中期材料上报',

					endTerm: '结项材料上报',

					news: '发布新闻稿',
				}
			}
		},
		methods: {
			linkTo(page) {
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
	}

	.left {
		.top {
			height: 150px;
			background-image: url('@/static/uploads/btn_bgi1.png');
			background-repeat: no-repeat;
			background-size: contain;

			.title {
				padding: 20px 0;
				width: 80%;
				margin: auto;
			}
		}

		.bottom {
			height: 70px;
			background-image: url('@/static/uploads/btn_bgi2.png');
			background-repeat: no-repeat;
			background-size: contain;
			display: flex;
			align-items: center;
			justify-content: start;

			.title {
				width: 80%;
				margin: auto;
			}
		}
	}

	.right {

		.title {
			width: 70px;
			margin-left: 20px;
			text-align: center;
		}

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
			height: 70px;
			background-image: url('@/static/uploads/btn_bgi5.png');
			background-repeat: no-repeat;
			background-size: contain;
			color: white;
			display: flex;
			align-items: center;
			justify-content: start;
		}
	}
</style>
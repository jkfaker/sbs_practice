<template>
	<view class="">
		<view v-if="text.length >=15" class="_notice" id="scroll_div">
			<view class="_noticeF">
				<view :style="styleName" id="srcoll-item">{{text}}</view>
			</view>
		</view>
		<view v-else class="title">
			{{ text }}
		</view>
	</view>

</template>

<script>
	export default {
		props: {
			//显示数据
			text: {
				type: String,
				defual: function() {
					return '';

				}
			},
		},
		computed: {},
		data() {
			return {
				styleName: {},
			};
		},
		mounted() {},
		methods: {
			animation() {
				let _this = this
				let view = uni.createSelectorQuery().in(_this).select("#srcoll-item");
				// console.log(view)
				uni.getSystemInfo({
					success: function(res) {
						view.fields({
							size: true,
							scrollOffset: true
						}, data => {
							let time = '16s' // 默认16s
							if (data) { // 根据文字宽度计算时间，可以自己修改
								let num
								let w = Number(data.width)
								if (w < (res.windowWidth + 40)) {
									num = Math.round(w / 20)
								} else {
									num = Math.round(w / 28)
								}
								time = num + 's'
							}
							_this.styleName = {
								'animation-duration': time
							}
						}).exec();
					}
				});

			}
		}
	};
</script>

<style lang="scss">
	.title {
		margin-left: 10px;
		line-height: 30px;
		width: 100%;
		font-weight: 400;
		color: white;
	}

	#scroll_begin,
	#scroll_end {
		display: inline-block;
	}

	._notice {
		margin-left: 10px;
		line-height: 30px;

		font-weight: 400;
		color: white;
		// position: fixed;
		left: 0;
		top: 0;
		/* #ifdef H5 */
		// top: 88upx;
		/* #endif */
		z-index: 9;
		width: 100%;
		font-size: 18px;
		height: 30px;
		border-radius: 6upx;
		line-height: 20px;
		overflow: hidden;
		box-sizing: border-box;
		padding: 0 80%;
		white-space: nowrap;
	}

	._noticeF {
		position: relative;
	}

	#srcoll-item {
		position: absolute;
		white-space: nowrap;
		width: auto;
		top: 0px;
		animation: scroll 4s linear infinite both;
		word-break: keep-all;
	}

	@keyframes scroll {
		0% {
			transform: translateX(0);
		}

		100% {
			transform: translateX(-100%);
		}
	}
</style>
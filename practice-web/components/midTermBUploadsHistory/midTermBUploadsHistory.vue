<template>
	<view class="">
		<u-sticky zIndex="-1">
			<uni-section class="section" title="上传记录" type="line"></uni-section>
		</u-sticky>
		<view class="content">
			<!-- 内容为空时显示 -->
			<div style="margin: 15%;" v-if="!data.length">
				<u-empty mode="data" textSize="20" height="300" width="300" icon="/static/icon/empty.png">
				</u-empty>
			</div>
			<u-gap height="10"></u-gap>
			<view class="list">
				<view v-for="(item, index) in data" :key="index">
					<view class="item">
						<view class="item-content">
							<text> 上传人: 自己 \n文件类型: {{ item.type }} \n点击下载:<text class="link" @click="linkClick(item)">
									{{item.fileName}}
								</text>\n状态：<text
									:style="item.label === 1? 'color:yellow': item.label === 2 ? 'color: green': 'color: red'">{{ label(item.label) }}</text>\n上传时间:{{ item.uploadTime }}</text>
						</view>
						<u-gap height="20"></u-gap>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: "midTermBUploadsHistory",
		props: ['data'],
		data() {
			return {
				// 文件临时路径
				tempFilePath: '',
			};
		},
		methods: {
			linkClick(item) {
				uni.downloadFile({
					url: getApp().globalData.filePath + item.fileName,
					success: (res) => {
						console.log(res);
						uni.saveFile({
							tempFilePath: this.tempFilePath,
							success: (res) => {
								console.log(res);
								this.$emit('loading', false);
								this.$emit('success', '文件下载成功');
							},
							fail: (error) => {
								console.log(error);
								// TODO: emit
								this.$emit('loading', false);
								this.$emit('error', '文件下载失败,请稍后重试');
							}
						})
					},
					fail: (error) => {
						console.log(error);
						this.$emit('loading', false);
						// TODO: emit
						this.$emit('error', '文件下载失败,请稍后重试');

					}
				})
				this.$emit('loading', true);
			},
			label(n) {
				switch (n) {
					case 1:
						return '待审核';
					case 2:
						return '已通过';
					case 3:
						return '已驳回';
				}
			}
		}
	}
</script>

<style lang="less" scoped>
	.item-content {
		white-space: pre-wrap;
		background-color: white;
	}

	.link {
		text-decoration-line: underline;
		color: blue;
	}
</style>
<template>
	<view>
		<!-- 顶部自定义导航栏 -->
		<uniNavBar :fixed="true" @clickRight="clickRightNavbar()">
			<block>
				<view class="nav-bar-title">
					社会实践
				</view>
			</block>
			<block slot="right">
				<uniBadge class="uni-badge-left-margin" :text="number" :offset="[4,4]" absolute="rightTop" size="small">
					<uni-icons type="email" size="30"></uni-icons>
				</uniBadge>
			</block>
		</uniNavBar>
	</view>
</template>

<script>
	import uniBadge from '@/uni_modules/uni-badge/components/uni-badge/uni-badge.vue';
	import uniNavBar from '@/uni_modules/uni-nav-bar/components/uni-nav-bar/uni-nav-bar.vue';

	export default {
		components: {
			uniBadge,
			uniNavBar
		},
		name: "indexAAHeaderBar",

		data() {
			return {
				number: 0,
			};
		},
		async created() {
			uni.showLoading({
				title: '正在加载',
				mask: 'true'
			});
			await this.getMessageCount();
		},
		methods: {
			clickRightNavbar() {
				uni.navigateTo({
					url: getApp().globalData.pagePath.announce
				})
			},
			async getMessageCount() {
				const PATH = '/user/announce/count';
				const res = await uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token')
					},
				})
				console.log(res);
				if (res.data.code === 0) {
					uni.showToast({
						title: res.data.msg,
						icon: 'error',
					})
					return;
				}
				console.log(res);
				this.number = res.data.data;
				uni.hideLoading();
			}
		}
	}
</script>

<style lang="less" scoped>
	.uni-badge-left-margin {
		margin-right: 10px;
	}

	.nav-bar-title {
		line-height: 44px;
		text-align: center;
		margin: auto;
		font-size: 12px;

	}
</style>
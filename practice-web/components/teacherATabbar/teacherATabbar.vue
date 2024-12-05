<template>
	<view>
		<u-tabbar :value="index" @change="handleChange($event)" :fixed="true" :placeholder="true"
			:safeAreaInsetBottom="true">

			<u-tabbar-item v-for="(item, index) in items" :text="item.text">
				<image mode="heightFix" class="img u-page__item__slot-icon" slot="active-icon" :src="item.activeIcon">
				</image>
				<image mode="heightFix" class="img u-page__item__slot-icon" slot="inactive-icon"
					:src="item.inactiveIcon"></image>
			</u-tabbar-item>

		</u-tabbar>
	</view>
</template>

<script>
	export default {
		name: "teacherATabbar",
		props: ['index'],
		data() {
			return {
				items: [{
						text: '首页',
						inactiveIcon: '/static/icon/index.png',
						activeIcon: '/static/icon/index_selected.png',
					}, {
						text: '风采',
						inactiveIcon: '/static/icon/show.png',
						activeIcon: '/static/icon/show_selected.png',
					}, {
						text: '我的',
						inactiveIcon: '/static/icon/mine.png',
						activeIcon: '/static/icon/mine_selected.png',
					}

				]
			};
		},
		methods: {
			handleChange(index) {
				console.log(index);
				// 各个网址
				let url = '';
				switch (index) {
					case 0:
						url = getApp().globalData.pagePath['tIndex'];
						break;
					case 1:
						url = getApp().globalData.pagePath['tShow'];
						break;
					case 2:
						url = getApp().globalData.pagePath['tMine'];
						break;
					case 3:
						break;
				}
				console.log(url);
				uni.redirectTo({
					url,
				})

			}
		}
	}
</script>

<style lang="less" scoped>
	.img {
		height: 23px;
	}
</style>
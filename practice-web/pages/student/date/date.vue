<template>
	<!-- 打卡打卡打卡 -->
	<view>
		<u-modal :content="modalContent" :show="modalShow" confirmText="确认打卡" showCancelButton closeOnClickOverlay
			@confirm="handleClick()" @cancel="modalShow = false" @close="modalShow = false"></u-modal>
		<u-loading-page bgColor="rgba(0, 0, 0, 0.3)" fontSize="25" iconSize="40" loadingColor="#ddd" color="#eee"
			:loadingText="loadingText" loading-mode="spinner" :loading="loading">
		</u-loading-page>
		<!-- 打卡成功或失败提示消息 -->
		<u-notify ref="uNotify"></u-notify>
		<view>
			<uni-calendar class="uni-calendar--hook" :lunar="true" :selected="info.selected" :showMonth="true"
				@change="change" @monthSwitch="monthSwitch()" />
			<u-gap height="20"></u-gap>
			<view class="button">
				<u-button @click="handleClick()" type="primary" size="large" text="安 全 打 卡"></u-button>
			</view>
		</view>
	</view>
</template>

<script>
	/**
	 * 获取距今天多少天的时间
	 */
	function getDate(date, AddDayCount = 0) {
		if (!date) {
			date = new Date();
		}
		if (typeof date !== 'object') {
			date = date.replace(/-/g, '/');
		}
		const dd = new Date(date);

		dd.setDate(dd.getDate() + AddDayCount); // 获取AddDayCount天后的日期

		const y = dd.getFullYear();
		const m = dd.getMonth() + 1 < 10 ? '0' + (dd.getMonth() + 1) : dd.getMonth() + 1; // 获取当前月份的日期，不足10补0
		const d = dd.getDate() < 10 ? '0' + dd.getDate() : dd.getDate(); // 获取当前几号，不足10补0
		return {
			fullDate: y + '-' + m + '-' + d,
			year: y,
			month: m,
			date: d,
			day: dd.getDay()
		}
	}
	export default {
		components: {},
		data() {
			return {
				showCalendar: false,
				info: {
					selected: []
				},
				modalShow: false,
				modalContent: '打卡前请负责人确保团队所有外出人员安全,如安全问题请及时联系指导老师!',
				loadingText: '正在打卡', // 打卡时的文字
				loading: false, //是否加载中

			}
		},
		onLoad() {
			this.getData();
		},
		methods: {
			getData() {
				const PATH = '/date/show';
				uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						console.log(res);
						this.info.selected = res.data.data;
					},
					fail: (error) => {
						this.notify('error', '网络异常,请稍后重试');
						console.log(error);
					}
				})
			},
			change(e) {
				console.log('change 返回:', e);
				// 模拟动态打卡
				return;
				// TODO
				this.info.selected.push({
					date: e.fulldate,
					info: '已打卡'
				});
			},
			// 点击打卡按钮触发
			handleClick() {
				const path = '/date/clock';
				if (!this.modalShow) {
					this.modalShow = true;
					return;
				}
				uni.request({
					url: getApp().globalData.URL + path,
					method: 'POST',
					header: {
						'token': uni.getStorageSync('token'),
					},
					data: {
						date: getDate().fullDate,
						info: '已打卡'
					},
					success: (res) => {
						console.log(res);
						this.loading = false;
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						this.notify('success', '打卡成功');
						this.getData();
						console.log(res);

					},
					fail: (error) => {
						this.loading = false;
						this.notify('error', '网络异常！');
						console.log(error);
					}
				})
				this.loading = true;
				this.modalShow = false;
				this.getData();
				return;
			},


			monthSwitch(e) {
				console.log('monthSwitchs 返回:', e);
			},
			// 错误提示
			notify(type, message) {
				this.$refs.uNotify.show({
					top: 10,
					type,
					message,
					duration: 1000 * 3,
					fontSize: 20,
				})
			}
		},
	}
</script>

<style lang="scss" scoped>
	.button {
		width: 90%;
		margin: auto;
	}
</style>
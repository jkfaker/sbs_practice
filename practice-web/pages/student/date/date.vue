<template>
	<!-- 打卡打卡打卡 -->
	<view>
		<u-modal :content="modalContent" :show="modalShow" confirmText="确认打卡" showCancelButton closeOnClickOverlay
			@confirm="handleClock()" @cancel="modalShow = false" @close="modalShow = false"></u-modal>
		<u-modal :content="startContent" :show="startShow" confirmText="开始打卡" showCancelButton closeOnClickOverlay
			@confirm="handleStart()" @cancel="startShow = false" @close="startShow = false"></u-modal>
		<u-modal :content="finishContent" :show="finishShow" confirmText="结束打卡" showCancelButton closeOnClickOverlay
			@confirm="handleFinish()" @cancel="finishShow = false" @close="finishShow = false"></u-modal>
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
				<!-- 根据dataCode动态显示不同按钮 -->
				<view v-if="dataCode === '未开始'" class="button">
					<u-button @click="handleStart()" type="primary" size="large" text="开 始 打 卡" />
				</view>
				<view v-else-if="dataCode === '未打卡'" class="button">
					<u-button @click="handleClock()" type="primary" size="large" text="今 日 打 卡" />
					<u-gap height="15"></u-gap>
					<u-button @click="handleFinish()" type="error" size="large" text="结 束 打 卡" />
				</view>
				<view v-else-if="dataCode === '已打卡'" class="button">
					<u-button disabled type="primary" size="large" text="今 日 已 打 卡" />
				</view>
				<view v-else-if="dataCode === '已结束'" class="button">
					<u-button disabled type="primary" size="large" text="打 卡 已 结 束" />
				</view>

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
				// 打卡弹框显示状态
				modalShow: false,
				// 开始打卡弹框显示状态
				startShow: false,
				// 结束打卡弹框显示状态
				finishShow: false,
				modalContent: '打卡前请负责人确保团队所有外出人员安全,如安全问题请及时联系指导老师!',
				startContent: '社会实践每个团队均有且只有一次启动打卡的机会。在启动打卡后，需要连续打卡，如当天未打卡管理员会联系项目负责人询问情况，结束出勤后点击结束打卡',
				finishContent: '请确定团队外出实践是否已经完成。如不是，请返回并进行今日打卡。',
				loadingText: '正在打卡', // 打卡时的文字
				loading: false, //是否加载中
				dateInfo: ['未打卡', '已打卡', '未开始', '已结束'], // 枚举info的四种情况
				deteCode: "" // 为上面dateInfo的index
			}
		},
		onLoad() {
			this.getData();
		},
		methods: {
			getData() {
				const PATH = '/user/date/show';
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
						if (this.info.selected.length === 0) {
							// 未开始
							this.dataCode = "未开始";
						} else {
							// 检查是否存在 "已结束" 的情况
							const hasEnded = this.info.selected.some(item => item.info === '结束打卡');
							if (hasEnded) {
								this.dataCode = "已结束";
							} else {
								// 判断是否包含今天日期的项
								const today = new Date();
								const formattedToday =
									`${today.getFullYear()}-${String(today.getMonth() + 1).padStart(2, '0')}-${String(today.getDate()).padStart(2, '0')}`;
								const hasToday = this.info.selected.some(item => item.date === formattedToday);
								this.dataCode = hasToday ? "已打卡" : "未打卡";
							}
						}
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
			handleClock() {
				const PATH = '/user/date/clock';
				if (!this.modalShow) {
					this.modalShow = true;
					return;
				}
				uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'POST',
					header: {
						'token': uni.getStorageSync('token'),
					},
					data: {
						date: getDate().fullDate,
						info: "已打卡"
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
			// 开始打卡
			handleStart() {
				const PATH = '/user/date/clock';
				if (!this.startShow) {
					this.startShow = true;
					return;
				}
				uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'POST',
					header: {
						'token': uni.getStorageSync('token'),
					},
					data: {
						date: getDate().fullDate,
						info: '开始'
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
				this.startShow = false;
				this.getData();
				return;
			},
			// 结束打卡
			handleFinish() {
				const PATH = '/user/date/clock';
				if (!this.finishShow) {
					this.finishShow = true;
					return;
				}
				uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'POST',
					header: {
						'token': uni.getStorageSync('token'),
					},
					data: {
						date: getDate().fullDate,
						info: '结束'
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
				this.finishShow = false;
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
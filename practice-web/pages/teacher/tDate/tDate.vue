<template>
	<view class="container">
		<view class="background">
		</view>
		<u-notify ref="uNotify"></u-notify>
		<uniNavBar height='44' :fixed="true" @clickLeft="handleReturn()" @clickRight="handleChooseDate()"
			:border="false" shadow left-icon="left" leftText="返回" rightIcon="calendar" title="安全打卡审核">
		</uniNavBar>
		<uni-popup ref="popup" background-color="#fff" borderRadius="10px" @change="change">
			<view class="popup-content">
				<view class="">
					学生项目详情：
				</view>
				<view class="">
					项目标题：{{ project.title }}
				</view>
				<view class="">
					负责人学号：{{ project.leaderId }}
				</view>
				<view class="">
					负责人姓名：{{ project.leaderName }}
				</view>
				<view class="">
					负责人班级：{{ project.leaderClass }}
				</view>
				<view class="">
					项目级别：{{ project.label }}
				</view>
				<view class="">
					项目主题：{{ project.subjectId }}
				</view>
			</view>
		</uni-popup>
		<uni-calendar ref="calendar" class="uni-calendar--hook" :clear-date="true" :date="date" :insert="info.insert"
			:lunar="info.lunar" :startDate="info.startDate" :endDate="info.endDate" :range="info.range"
			@confirm="dateConfirm($event)" />
		<view class="date">
			{{ date }}
		</view>
		<view class="box">
			<view v-if="!data.length" style="margin: auto; color: green; padding: 20px;">
				该学院暂无项目负责人
			</view>
			<view class="info">
				<!-- 遍历每个状态组 -->
				<view v-for="(group, status) in groupedData" :key="status" class="status-group">
					<!-- 组标题 -->
					<view class="group-title">{{ status }}</view>

					<!-- 项目容器 -->
					<view class="items-container">
						<!-- 有数据时显示项目 -->
						<view v-if="group.length > 0" class="item-wrapper">
							<view v-for="item in group" :key="item.projectId" class="item" @click="projectDetails(item)"
								@longpress="longpress(item)">
								<view class="icon-container">
									<image class="sign-img" mode="widthFix" :src="getIconPath(status)" />
								</view>
								<view class="leader_name">{{ item.leaderName }}</view>
							</view>
						</view>

						<!-- 无数据时显示提示 -->
						<view v-else class="empty-tip">此数据为空</view>
					</view>
				</view>
			</view>
		</view>
		<view class="count">
			<view v-for="(group, status) in groupedData" class="">
				{{ status }}：{{ group.length }} 人
			</view>
			<view class="">
				总计：{{ data.length }} 人
			</view>
		</view>

		<view class="footer">
			长按图标复制对应负责人电话
		</view>
		<u-gap height="5"> </u-gap>
	</view>
</template>

<script>
	import uniNavBar from '@/uni_modules/uni-nav-bar/components/uni-nav-bar/uni-nav-bar.vue';
	import uniPopup from '@/uni_modules/uni-popup/components/uni-popup/uni-popup.vue';

	export default {
		components: {
			uniNavBar,
			uniPopup
		},
		data() {
			return {
				date: '',
				icon: {
					unsign: require('@/static/icon/unsign.png'),
					signed: require('@/static/icon/signed.png'),
					unstart: require('@/static/icon/unstart.png'),
					finished: require('@/static/icon/finished.png'),
				},
				info: {
					lunar: true,
					range: true,
					insert: false,
					selected: []
				},
				data: [],
				// 项目详情
				project: {},
			}
		},
		onLoad() {
			this.getDate();
			this.getData();
		},
		computed: {
			// 将数据按状态分组
			groupedData() {
				// 动态计算状态
				const statusData = this.data.map(item => {
					const currentDate = this.date ? new Date(this.date) : new Date();
					const start = item.startTime ? new Date(item.startTime) : null;
					const end = item.endTime ? new Date(item.endTime) : null;

					let status = '未开始';

					// 情况1：未设置时间（startTime和endTime都为null）
					if (!start) {
						status = '未开始';
					}
					// 情况2：已设置时间但当时未开始
					else if (currentDate < start) {
						status = '未开始';
					}
					// 情况3：进行中时间段
					else if (!end || currentDate <= end) {
						status = item.info ? '已打卡' : '未打卡';
					}
					// 情况4：已结束
					else if (currentDate > end) {
						status = '已结束';
					}

					// 兼容后端返回的结束状态
					if (item.info === '结束') status = '已结束';

					return {
						...item,
						_status: status
					};
				});

				// 按状态分组
				return {
					'未开始': statusData.filter(item => item._status === '未开始'),
					'未打卡': statusData.filter(item => item._status === '未打卡'),
					'已打卡': statusData.filter(item => item._status === '已打卡'),
					'已结束': statusData.filter(item => item._status === '已结束')
				};
			}
		},
		methods: {
			// 根据状态返回图标路径
			getIconPath(info) {
				const icons = {
					'未打卡': this.icon.unsign,
					'已打卡': this.icon.signed,
					'未开始': this.icon.unstart,
					'已结束': this.icon.finished // 匹配后端返回的"结束"
				};
				return icons[info] || this.icon.unsign; // 未知状态默认未签到
			},

			projectDetails(item) {
				const PATH = '/teacher/project/one';
				uni.request({
					url: `${getApp().globalData.URL}${PATH}?id=${item.projectId}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						console.log(res);
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						this.project = res.data.data;
						this.$refs.popup.open();
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})
				this.$refs.popup.open();
			},
			// 长按复制学生电话
			longpress(item) {
				uni.setClipboardData({
					data: item.leaderPhone,
				})
			},
			// 处理点击预览
			handleChooseDate() {
				this.$refs.calendar.open();
			},
			dateConfirm(e) {
				const y = e.year;
				const m = e.month + 1 < 10 ? '0' + e.month : e.month; // 获取当前月份的日期，不足10补0
				const d = e.date < 10 ? '0' + e.date : e.date; // 获取当前几号，不足10补0
				const date = `${y}-${m}-${d}`;
				console.log('confirm 返回:', date);
				this.date = date;
				this.getData();
			},
			// 自定义导航栏返回处理
			handleReturn() {
				uni.navigateBack();
			},
			getDate() {
				const date = new Date();
				const y = date.getFullYear();
				const m = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1; // 获取当前月份的日期，不足10补0
				const d = date.getDate() < 10 ? '0' + date.getDate() : date.getDate(); // 获取当前几号，不足10补0
				this.date = y + '-' + m + '-' + d;
			},
			getData() {
				const PATH = '/teacher/date';
				uni.request({
					url: `${getApp().globalData.URL}${PATH}?date=${this.date}`,
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
						this.data = res.data.data;
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})
			},
			notify(type, message) {
				this.$refs.uNotify.show({
					top: 10,
					type,
					message,
					duration: 1000 * 3,
					fontSize: 20,
				})
			}
		}
	}
</script>

<style lang="less" scoped>
	.container {
		width: 90%;
		margin: auto;
	}

	.background {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: -1;
		background-color: #efeded;
	}

	.date {
		margin: 10px 0 5px 5px;
	}

	.popup-content {
		padding: 20px;
		white-space: pre-wrap;
		display: flex;
		justify-content: center;
		flex-direction: column;
		align-items: center;
	}



	.box {
		min-height: 70px;
		background-color: white;
		border-radius: 10px;
		// padding: 10px 15px;
		box-shadow: 1px 3px 2px rgba(0, 0, 0, 0.3);

		.info {

			display: flex;
			flex-direction: column;

			.item {
				display: flex;
				flex-direction: column;
				align-items: center;
				// width: 50px;
				/* 固定宽度保持整齐 */
				margin: 6px;

				// display: flex;
				// flex-direction: column;
				// align-items: center;
				// margin: 10px 5px;
				.sign-img {
					width: 40px;
					height: 40px;
				}


				.leader_name {
					line-height: 5px;
					font-size: 12px;
					text-align: center;
				}
			}
		}

	}


	.status-group {
		background-color: #f8f8f8;
		border-radius: 8px;
		padding: 10px;
	}

	.group-title {
		font-size: 16px;
		font-weight: bold;
		color: #333;
		margin-bottom: 10px;
		padding-left: 5px;
	}

	.items-container {
		background-color: white;
		border-radius: 8px;
		padding: 10px;
	}

	.item-wrapper {
		display: flex;
		flex-wrap: wrap;
		/* 允许换行 */
		// gap: 15px;
		/* 项目间间距 */
	}



	.empty-tip {
		color: #999;
		font-size: 14px;
		text-align: center;
		padding: 10px;
	}

	.count {
		margin: 10px;
		font-size: 14px;
	}

	.footer {
		color: gray;
		font-size: 12px;
		margin: 10px;
	}
</style>
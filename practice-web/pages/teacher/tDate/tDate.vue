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
			<view v-if="!data.length" style="margin: auto; color: green;">
				该学院暂无项目负责人
			</view>
			<view class="info">
				<view class="item" @click="projectDetails(item)" v-for="(item, index) in data" :key="index"
					@longpress="longpress(item)">
					<view class="">
						<image class="sign-img" mode="widthFix" :src="item.info === '已打卡' ? icon.signed : icon.unsign"
							:alt="item.info === '已打卡' ? '已签到' : '未签到'" />
					</view>
					<view class="leader_name">
						{{ item.leaderName }}
					</view>
				</view>
			</view>
		</view>
		<view class="count">
			<view class="">
				已打卡：{{ data.filter((item) => item.info === '已打卡' ).length }} 人
			</view>
			<view class="">
				总计：{{ data.length }} 人
			</view>
		</view>

		<view class="footer">
			长按图标复制对应负责人电话

		</view>
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
				},
				info: {
					lunar: true,
					range: true,
					insert: false,
					selected: []
				},
				data: {},
				// 项目详情
				project: {

				},
			}
		},
		onLoad() {
			this.getDate();
			this.getData();
		},
		methods: {
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
		padding: 10px 15px;
		box-shadow: 1px 3px 2px rgba(0, 0, 0, 0.3);

		.info {
			display: flex;
			align-items: center;
			justify-content: start;
			flex-wrap: wrap;

			.item {
				display: flex;
				flex-direction: column;
				align-items: center;
				margin: 10px 5px;

				.sign-img {
					width: 40px;
				}

				.leader_name {
					line-height: 5px;
					font-size: 12px;
					text-align: center;
				}
			}
		}

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
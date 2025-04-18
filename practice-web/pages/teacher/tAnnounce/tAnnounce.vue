<template>
	<view class="container">
		<uni-calendar ref="calendar" class="uni-calendar--hook" :insert="false" :selected="calendarData" />
		<view>
			<!-- 提示窗示例 -->
			<uni-popup :is-mask-click="true" ref="popup" type="dialog">
				<uni-popup-dialog mode="input" v-model="sendText" type="warn" confirmText="发送" title="消息发送"
					@confirm="sendAnnounce()"></uni-popup-dialog>
			</uni-popup>
		</view>
		<!-- 正文 -->
		<view v-if="projectList.length" class="">
			<uni-collapse accordion v-model="listIndex">
				<uni-collapse-item class="" v-for="(item, index) in projectList">
					<!-- 标题 -->
					<template v-slot:title>
						<view class="collapse-item">
							<view class="collapse-title">
								{{ item.title }}
							</view>
							<view class="collapse-bottom">
								<text class="collapse-leader">
									负责人：{{ item.leaderName }}
								</text>
								{{ ' ' }}
								<text
									:style="{ color: item.label === '校级立项' ? 'green' : item.label === '院级立项' ? 'blue' : 'red' }">
									{{ item.label }} <!-- 显示 label 的文本 -->
								</text>
							</view>
						</view>
					</template>
					<!-- 折叠内 -->
					<view class="content">


						<!-- 材料上交情况 -->
						<uni-section type="circle" title="材料上交情况"></uni-section>
						<uni-list>
							<uni-list-item v-for="(item, index) in fileList">
								<template v-slot:header>
									<view class="collapse-body">
										<view class="left">
											<uni-link font-size="17" color="black" :href="fileUrl(item)"
												:text="item.type"></uni-link>
											<text style="font-size: 12px;">(点击下载) </text>
										</view>
									</view>
								</template>
								<template v-slot:footer>
									<view class=" collapse-footer">
										<view
											:style="item.label === 1 ? 'color: yellow' : item.label === 2 ? 'color:green': 'color:red' "
											class="label">
											{{ item.label === 1 ? '待审核' : item.label === 2 ? '已通过' : '已驳回' }}
										</view>
										<view class="time">
											{{ item.uploadTime }}
										</view>
									</view>

								</template>
							</uni-list-item>
						</uni-list>


						<!-- 新闻稿情况 -->
						<uni-section v-if="newsList.length" type="circle" title="新闻稿"></uni-section>
						<uni-list>
							<uni-list-item v-for="(item, index) in newsList">
								<template v-slot:header>
									<view class="collapse-body">
										<view class="left">
											<uni-link font-size="17" color="black" :text="item.title"></uni-link>
										</view>
									</view>
								</template>
								<template v-slot:footer>
									<view class="collapse-footer">
										<view
											:style="item.label === 1 ? 'color: yellow' : item.label === 2 ? 'color:green': 'color:red' "
											class="label">
											{{ item.label === 1 ? '待审核' : item.label === 2 ? '已通过' : '已驳回' }}
										</view>
										<view class="time">
											{{ item.uploadTime }}
										</view>
									</view>
								</template>
							</uni-list-item>
						</uni-list>


						<!-- 打卡情况 -->
						<uni-section type="circle" title="打卡情况"></uni-section>
						<view class="">
							<button class="calendar-button" type="button" @click="openCalendar()">点击查看</button>
						</view>
						<!-- 消息发送记录 -->
						<uni-section type="circle" title="消息发送记录"></uni-section>
						<uni-list>
							<uni-list-item v-for="(item, index) in announceList">
								<template v-slot:header>
									<view class="collapse-body">
										<view class="left">
											<view>
												{{ item.text.length > 20 ? item.text.slice(0, 20) + '...' : item.text }}
											</view>
										</view>
									</view>
								</template>
								<template v-slot:footer>
									<view class="collapse-footer">
										<view :style="item.isRead === true ? 'color: green' : 'color:red' "
											class="label">
											{{ item.isRead === true ? '已读' : '未读' }}
										</view>
										<view class="time">
											{{ item.createTime }}
										</view>
									</view>
								</template>
							</uni-list-item>
						</uni-list>
						<view class="">
							<button class="announce-button" type="button" @click="openPopup()">向他发送消息</button>
						</view>
					</view>
				</uni-collapse-item>
			</uni-collapse>
		</view>
		<view v-if="!projectList.length" class="">
			<u-empty mode="data" textSize="20" height="300" width="300" icon="/static/icon/empty.png">
			</u-empty>
		</view>
	</view>
</template>

<script>
	import uniList from '@/uni_modules/uni-list/components/uni-list/uni-list.vue';
	import uniNavBar from '@/uni_modules/uni-nav-bar/components/uni-nav-bar/uni-nav-bar.vue';
	import uniPopup from '@/uni_modules/uni-popup/components/uni-popup/uni-popup.vue';
	import uniCollapse from '@/uni_modules/uni-collapse/components/uni-collapse/uni-collapse.vue';
	import uniCalendar from '@/uni_modules/uni-calendar/components/uni-calendar/uni-calendar.vue';
	export default {
		components: {
			uniList,
			uniNavBar,
			uniPopup,
			uniCollapse,
			uniCalendar
		},
		data() {
			return {
				projectList: [],
				// 下面三个都是item里面的
				modalText: '',
				messageType: 0,
				label: 0,
				// 点击的第几个面板index
				listIndex: "",
				// 点击面板后，获取该项目项目详情
				projectDetail: {},

				fileList: [],
				newsList: [],
				announceList: [],
				projectId: 0,
				// 需要发送的消息
				sendText: "",
				// 个人打卡数据
				calendarData: []
			}
		},
		async onLoad() {
			uni.showLoading({
				title: '正在加载',
				mask: true,
			})
			await this.getProject();
			uni.hideLoading();
		},
		watch: {
			// 点击面板后要展开详细信息
			async listIndex() {
				const index = this.listIndex;
				// 面板没有展开
				if (index === "")
					return;
				// 找到展开面板的projectId
				console.log(index);

				this.projectId = this.projectList[+index].id;
				uni.showLoading({
					title: '正在加载',
					mask: true,
				})
				await this.getAllFilesOfOne();
				await this.getAllNewsOfOne();
				await this.getAllAnnounce();
				uni.hideLoading();
			}
		},
		methods: {
			async openCalendar() {
				uni.showLoading({
					title: '正在加载',
					mask: true,
				})
				await this.getCalendarData();
				uni.hideLoading();
				this.$refs.calendar.open();
			},
			// 获取学生打卡记录
			async getCalendarData() {
				const PATH = '/teacher/date/one';
				const res = await uni.request({
					url: `${getApp().globalData.URL}${PATH}?projectId=${this.projectId}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
				})
				if (res.data.code === 0) {
					this.notify('error', res.data.msg);
					return;
				}
				console.log(res);
				this.calendarData = res.data.data;
			},
			async sendAnnounce() {
				const PATH = '/teacher/announce/send';
				const data = {};
				data.projectId = this.projectId;
				data.text = this.sendText;
				const res = await uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'POST',
					header: {
						'token': uni.getStorageSync('token'),
					},
					data
				});
				console.log(res);
				// error
				if (res.data.code === 0) {
					uni.showToast({
						title: res.data.msg,
						icon: 'error',
						mask: true
					})
					return;
				}
				await this.getAllAnnounce();
			},
			// 向他发消息，打开弹窗
			openPopup() {
				this.$refs.popup.open();
			},
			// 单个文件下载链接
			fileUrl(item) {
				return '/practice' + getApp().globalData.filePath + item.fileName;
			},
			async getProject() {
				const PATH = '/teacher/project/select';
				const res = await uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					}
				});
				console.log(res);
				// error
				if (res.data.code === 0) {
					uni.showToast({
						title: res.data.msg,
						icon: 'error',
						mask: true
					})
					return;
				}
				this.projectList = res.data.data;
			},
			// 获取该项目所有新闻稿
			async getAllNewsOfOne() {
				const PATH = '/teacher/news/one';
				const res = await uni.request({
					url: `${getApp().globalData.URL}${PATH}?projectId=${this.projectId}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					}
				});
				console.log(res);
				// error
				if (res.data.code === 0) {
					uni.showToast({
						title: res.data.msg,
						icon: 'error',
						mask: true
					})
					return;
				}
				this.newsList = res.data.data;
			},
			// 获取该项目的所有文件
			async getAllFilesOfOne() {
				const PATH = '/teacher/files/all/one';
				const res = await uni.request({
					url: `${getApp().globalData.URL}${PATH}?projectId=${this.projectId}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					}
				});
				console.log(res);
				// error
				if (res.data.code === 0) {
					uni.showToast({
						title: res.data.msg,
						icon: 'error',
						mask: true
					})
					return;
				}
				this.fileList = res.data.data;
			},
			// 获取与该项目的聊天记录
			async getAllAnnounce() {
				const PATH = '/teacher/announce/one';
				const res = await uni.request({
					url: `${getApp().globalData.URL}${PATH}?projectId=${this.projectId}`,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					}
				});
				console.log(res);
				// error
				if (res.data.code === 0) {
					uni.showToast({
						title: res.data.msg,
						icon: 'error',
						mask: true
					})
					return;
				}
				this.announceList = res.data.data;
			}
		}
	}
</script>

<style lang="less" scoped>
	.collapse-title {
		margin-top: 12px;
		text-align: center;
		margin-bottom: 5px;
	}

	.collapse-bottom {
		margin-left: 12px;
		font-size: 14px;
		margin-bottom: 5px;

		.collapse-leader {
			margin-right: 16px;
		}
	}

	.collapse-footer {
		.label {
			font-size: 14px;
		}

		.time {
			color: grey;
			font-size: 10px;
		}
	}

	.calendar-button {
		flex: 1;
		font-weight: bold;
		font-size: 32rpx;
	}
</style>
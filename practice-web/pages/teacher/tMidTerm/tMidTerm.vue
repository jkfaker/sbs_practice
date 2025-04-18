<template>
	<view>
		<view class="background">
		</view>
		<u-notify ref="uNotify"></u-notify>
		<uniNavBar height='44' :fixed="true" @clickLeft="handleReturn()" :border="false" shadow left-icon="left"
			leftText="返回" title="中期材料审核">
		</uniNavBar>
		<uni-popup ref="popup" background-color="#fff" borderRadius="10px" @change="change">
			<view class="popup-content">
				<view class="">
					本专题所有学生上传文件
				</view>
				<view class="">
					<uni-link color="green" :href="download()" text="点我下载"></uni-link>
				</view>
				<view class="">
					10分钟内有效，过期请刷新。
				</view>
				<view>点击周围蒙版可取消</view>
			</view>
		</uni-popup>
		<uni-segmented-control @clickItem="clickSegment($event)" :values="items" styleType="text"
			activeColor="#dd524d"></uni-segmented-control>
		<uni-search-bar style="width: 90%;margin: auto;" color="#FFFFFF" radius="10" placeholder="请输入标题或负责人姓名"
			v-model="searchText"></uni-search-bar>
		<view class="content">
			<!--  empty  -->
			<view v-if="!data.length" class="">
				<u-empty mode="data" textSize="20" height="300" width="300" icon="/static/icon/empty.png">
				</u-empty>
			</view>

			<u-gap height="10"></u-gap>
			<view class="list" v-for="(item, index) in data" :key="index">
				<view class="item">
					<view class="info">
						<view class="title">
							<text selectable>{{ item.title }}</text>
						</view>
						<view class="leader-name">
							负责人： <text selectable>{{ item.leaderName }}</text>
						</view>
						<view class="leader-class">
							项目类型：<text selectable
								:style=" 'color:' + (item.label === '校级立项' ? 'rgb(29, 184, 78)' : item.label === '院级立项' ? 'rgb(28, 37, 212)' : 'red')">{{ item.label ?  item.label : '未评定' }}</text>
						</view>
						<view class="leader-phone">
							联系电话：<text selectable>{{ item.leaderPhone }}</text>
						</view>
						<view class="file">
							项目文件：<uni-link color="green" :href="fileUrl(item)" :text="item.fileName"></uni-link>
						</view>
						<view class="create-time">
							上传时间：<text selectable>{{ item.createTime }}</text>
						</view>
					</view>
					<u-gap height="5"></u-gap>
					<view class="buttons">
						<view @click="changeLabel(item, 3)"
							:style=" 'backgroundColor: ' + (item.fileLabel === 3 ? 'rgb(231, 85, 37)' : 'rgba(0,0,0,0.2)' )">
							驳回
						</view>
						<view @click="changeLabel(item, 2)"
							:style=" 'backgroundColor: ' + (item.fileLabel === 2 ? 'rgb(22,195,28)' : 'rgba(0,0,0,0.2)' )">
							通过
						</view>
					</view>
				</view>
				<u-gap height="5"></u-gap>
			</view>
		</view>
		<view class="bottom" v-if="multi === 1">
			<view>校级</view>
			<view>院级</view>
		</view>
		<view class="download" @click="showPopup()" v-if='multi === 0'>
			<uni-icons type="download" size="27"></uni-icons>
			<view class="">
				批量下载
			</view>
		</view>
	</view>
</template>

<script>
	import uniSegmentedControl from '@/uni_modules/uni-segmented-control/components/uni-segmented-control/uni-segmented-control.vue';
	import uniNavBar from '@/uni_modules/uni-nav-bar/components/uni-nav-bar/uni-nav-bar.vue';
	import uniLink from '@/uni_modules/uni-link/components/uni-link/uni-link.vue';
	import uniPopup from '@/uni_modules/uni-popup/components/uni-popup/uni-popup.vue';
	export default {
		components: {
			uniSegmentedControl,
			uniNavBar,
			uniLink,
			uniPopup
		},
		data() {
			return {
				searchText: '',
				data: '',
				// 控制分段栏
				subjects: [],
				subjectId: 0,
				// 批量审核
				multi: 0,
				// 批量文件下载文件名
				fileName: '',
			}
		},
		async onLoad() {
			await this.getSubject();
			this.subjectId = this.subjects[0].id;
			this.searchText = '';
			this.getData();
		},
		computed: {
			items: function() {
				return this.subjects.map((item) => item.name);
			}
		},
		methods: {
			// 点击批量下载下载本专题所有项目文件
			showPopup() {
				const PATH = '/teacher/files/download';
				uni.request({
					url: `${getApp().globalData.URL}${PATH}?subjectId=${this.subjectId}&fileType=2`,
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
						this.fileName = res.data.data;
						// 显示弹出层
						this.$refs.popup.open();
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})
			},
			// 单个文件下载链接
			fileUrl(item) {
				return '/practice' + getApp().globalData.filePath + item.fileName;
			},
			// 自定义导航栏返回处理
			handleReturn() {
				uni.navigateBack();
			},
			// 批量审核
			multiChoice() {
				if (this.multi === 1) {
					this.multi = 0;
					return;
				}
				this.multi = 1;
				// 显示多选

			},
			// 获取主题
			async getSubject() {
				const PATH = '/teacher/subject';
				const res = await uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					}
				})
				if (res.data.code === 0) {
					this.notify('error', res.data.msg);
					return;
				}
				this.subjects = res.data.data;
			},

			getData() {
				const PATH = '/teacher/project/files';
				uni.request({
					url: `${getApp().globalData.URL}${PATH}?subjectId=${this.subjectId}&fileType=2&projectName=${this.searchText}`,
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
			// 点击分段器
			clickSegment(e) {
				this.subjectId = this.subjects[e.currentIndex].id;
				this.getData();
			},
			// 更新通过和驳回标签
			async changeLabel(item, label) {
				console.log(item.fileId);
				const PATH = '/teacher/files/mid/label';
				const res = await uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'POST',
					data: {
						id: item.fileId,
						label: label,
					},
					header: {
						'token': uni.getStorageSync('token'),
					},
				})
				if (res.data.code === 0) {
					this.notify('error', res.data.msg);
					return;
				}
				// 刷新
				console.log(this.subjectId);
				this.getData();
			},
			// 处理批量文件点击下载事件
			download() {
				return this.fileName;
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
		watch: {
			// 处理搜索框的搜索功能
			async searchText(value) {
				clearTimeout(this.timer);
				this.timer = setTimeout(async () => {
					this.page = {};
					await this.getData();
				}, 1000)

			}
		}
	}
</script>

<style lang="less" scoped>
	.background {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: -1;
		background-color: #efeded;
	}

	.popup-content {
		height: 120px;
		width: 250px;
		white-space: pre-wrap;
		display: flex;
		justify-content: center;
		flex-direction: column;
		align-items: center;
	}

	.content {
		width: 90%;
		margin: auto;

		.item {
			background-color: white;
			box-shadow: 3px 3px 3px rgba(0, 0, 0, 0.2);
			padding: 5px;
			border-radius: 5px;

			.info {
				background-color: rgba(0, 0, 0, 0.1);
				border-radius: 5px 5px 0 0;
				padding: 2px 5px;

				.title {
					font-size: 14px;
					font-weight: bold;
				}

				.file {
					white-space: nowrap;
					overflow: hidden;
					/* 超出部分隐藏 */
					text-overflow: ellipsis;
					/* 超出部分用省略号表示 */
				}

				.leader-name,
				.leader-class,
				.leader-phone,
				.create-time,
				.file {
					font-size: 12px;
				}


				.create-time {
					color: gray;
				}
			}

			.buttons {
				display: flex;
				flex-direction: row;
				align-items: center;
				justify-content: space-around;

				view {
					border-radius: 10px;
					background-color: rgba(0, 0, 0, 0.15);
					width: 35%;
					text-align: center;
					text-indent: 14px;
					letter-spacing: 14px;
					padding: 5px 0;
					box-shadow: 1px 3px 2px rgba(0, 0, 0, 0.3);
				}
			}
		}
	}

	.bottom {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		height: 50px;
		z-index: 10;
		background-color: white;
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		align-items: center;

		view {
			border-radius: 10px;
			width: 35%;
			height: 50%;
			background-color: rgb(238, 189, 161);
			text-align: center;
			text-indent: 14px;
			letter-spacing: 14px;
			padding: 5px 0;
			box-shadow: 1px 3px 2px rgba(0, 0, 0, 0.3);
		}
	}

	.download {
		position: fixed;
		left: 10px;
		bottom: 10px;
		border-radius: 50%;
		font-size: 12px;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		background-color: rgb(238, 189, 161);
		width: 60px;
		height: 60px;
		box-shadow: 3px 3px 3px rgba(0, 0, 0, 0.3);
	}
</style>
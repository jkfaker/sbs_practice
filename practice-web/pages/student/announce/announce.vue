<template>
	<view class="container">
		<uni-popup ref="" type="center" :animation="true">
			<text>{{ modalText }}</text>
			<button @click="modalClose()">我已知晓</button>
		</uni-popup>
		<view>
			<!-- 提示窗示例 -->
			<uni-popup :is-mask-click="true" ref="popup" type="dialog">
				<uni-popup-dialog type="warn" :showClose="messageType === 1 ? true : false" cancelText="了解"
					:confirmText="messageType === 1 ? '去查看' : '我已知晓'" title="消息详情" :content="modalText"
					@confirm="detailModalConfirm()"></uni-popup-dialog>
			</uni-popup>
		</view>
		<view class="box-bg">
			<uniNavBar height='44' :fixed="true" @clickLeft="handleReturn()" @clickRight="showModal()" :border="false"
				shadow left-icon="left" leftText="返回" right-text="一键已读" title="消息列表">
			</uniNavBar>
		</view>
		<uni-list v-if="messageList.length">
			<uni-list :border="true">
				<!-- 头像显示圆点 -->
				<uni-list-chat v-for="(item, index) in messageList" :title="chatTitle(item)"
					avatar="https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/unicloudlogo.png" :note="item.text"
					:clickable="true" @click="messageClick(item)" :time="item.updateTime" badge-positon="left"
					:badge-text="item.isRead ? '' : 'dot'">
				</uni-list-chat>
			</uni-list>
		</uni-list>
		<view v-if="!messageList.length" class="">
			<u-empty mode="data" textSize="20" height="300" width="300" icon="/static/icon/empty.png">
			</u-empty>
		</view>
	</view>
</template>

<script>
	import uniList from '@/uni_modules/uni-list/components/uni-list/uni-list.vue';
	import uniNavBar from '@/uni_modules/uni-nav-bar/components/uni-nav-bar/uni-nav-bar.vue';
	import uniPopup from '@/uni_modules/uni-popup/components/uni-popup/uni-popup.vue';
	export default {
		components: {
			uniList,
			uniNavBar,
			uniPopup
		},
		data() {
			return {
				messageList: [],
				// 下面三个都是item里面的
				modalText: '',
				messageType: 0,
				label: 0
			}
		},
		async onLoad() {
			uni.showLoading({
				title: '正在加载',
				mask: true,
			})
			await this.getMessageList();
		},
		methods: {
			detailModalConfirm() {
				if (this.messageType === 1) {
					switch (this.label) {
						case 1:
							// 立项
							uni.navigateTo({
								url: getApp().globalData.pagePath.setup
							})
							break;
						case 2:
							// 项目中期
							uni.navigateTo({
								url: getApp().globalData.pagePath.midTerm
							})
							break;
						case 3:
							// 结项
							uni.navigateTo({
								url: getApp().globalData.pagePath.endTerm
							})
							break;
						case 4:
							// 新闻稿
							uni.navigateTo({
								url: getApp().globalData.pagePath.news
							})
							break;
					}
				}
			},
			// 处理点击消息，弹窗打开消息详情
			messageClick(item) {
				const PATH = '/user/announce/read/';
				uni.request({
					url: getApp().globalData.URL + PATH + item.id,
					method: 'POST',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						console.log(res);
						if (res.data.code === 0) {
							uni.showToast({
								title: res.data.msg,
								icon: 'error',
								mask: true
							})
							return;
						}
						this.getMessageList();
					},
					fail: (error) => {
						console.log(error);
						uni.showToast({
							title: '网络异常',
							icon: 'error',
							mask: true
						})
					}
				})
				this.$refs.popup.open();
				this.modalText = item.text;
				this.messageType = item.messageType;
				this.label = item.label;

			},
			chatTitle(item) {
				// 消息类型 为老师
				if (item.messageType === 2) {
					return item.teacherName + '老师';
				} else if (item.messageType === 1) {
					return '系统消息';
				}

			},
			async getMessageList() {
				const PATH = '/user/announce/all/list';
				const res = await uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					}
				});

				uni.hideLoading();
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

				this.messageList = res.data.data;
			},
			// navBar左按钮返回
			handleReturn() {
				uni.navigateBack();
			},
			// navbar右按钮全部已读
			async showModal() {
				// 1，弹出确认弹框
				const res = await uni.showModal({
					title: '一键已读',
					content: '是否清除全部新消息提醒',
					showCancel: true,
					confirmText: '清除',
					confirmColor: 'red',

				})
				// 用户确认
				if (res.confirm) {
					// 正在加载
					uni.showLoading({
						title: '正在加载',
						mask: true,
					})
					// 2，全部已读
					await this.handleAllRead();
				}
			},
			// 全部已读
			async handleAllRead() {
				const PATH = '/user/announce/all/read';
				const res = await uni.request({
					url: getApp().globalData.URL + PATH,
					method: 'POST',
					header: {
						'token': uni.getStorageSync('token'),
					},
				})

				console.log(res);
				// error
				if (res.data.code === 0) {
					uni.hideLoading();
					uni.showToast({
						title: res.data.msg,
						icon: 'error',
						mask: true
					})
					return;
				}
				// 3，刷新数据
				await this.getMessageList();
				// 4，提示所有已清除
				uni.showToast({
					title: '所有消息为已读',
					icon: 'success',
					mask: true
				})
			}
		}

	}
</script>

<style lang="less" scoped>

</style>
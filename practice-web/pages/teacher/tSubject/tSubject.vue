<template>
	<view class="container">
		<u-notify ref="uNotify"></u-notify>
		<uni-popup ref="alertDialog" type="dialog">
			<uni-popup-dialog type="error" cancelText="取消" confirmText="删除" title="主题删除" content="确定删除吗,可能导致已有项目无法查看!"
				@confirm="subDelete()"></uni-popup-dialog>
		</uni-popup>
		<view class="list" v-for="(item, index) in subjectList" :key="index">
			<uni-section :title="`主题${index + 1}`" type="line" padding>
				<uni-easyinput v-model="item.name" :styles="styles" :placeholderStyle="placeholderStyle"
					placeholder="请输入内容" @input="input"></uni-easyinput>
				<u-gap height="20"></u-gap>
				<view class="buttons">
					<button size="mini" type="warn" @click="clickDelete(item)">删除</button>
					<button size="mini" type="primary" @click="submit(item)">提交</button>
				</view>
			</uni-section>
		</view>
		<u-gap height="20"></u-gap>
		<button @click="addSubject()" size="default" type="primary">添加主题</button>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 主题列表
				subjectList: [],
				// 删除主题的id
				deleteId: 0,
			}
		},
		onLoad() {
			this.getAllSubject();
		},
		methods: {
			// 错误提示
			notify(type, message) {
				this.$refs.uNotify.show({
					top: 10,
					type,
					message,
					duration: 1000 * 3,
					fontSize: 20,
				})
			},
			// 点击删除按钮
			clickDelete(item) {
				this.deleteId = item.id;
				this.$refs.alertDialog.open();
			},
			// 获取主题数量
			getCount() {

			},
			// 添加主题
			submit(subject) {
				const PATH = '/teacher/subject/update';
				uni.request({
					url: getApp().globalData.URL + PATH,
					header: {
						'token': uni.getStorageSync('token'),
					},
					method: 'POST',
					data: subject,
					success: (res) => {
						console.log(res);
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						this.notify('success', '已完成数据变更');
						this.getAllSubject();
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})
			},
			// 添加主题
			addSubject() {
				this.subjectList.push({
					name: '',
				})
			},
			// 获取所有主题
			getAllSubject() {
				const PATH = '/teacher/subject';
				uni.request({
					url: getApp().globalData.URL + PATH,
					header: {
						'token': uni.getStorageSync('token'),
					},
					method: 'GET',
					success: (res) => {
						console.log(res);
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						this.subjectList = res.data.data;
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})
			},
			// 主题删除
			subDelete() {
				const PATH = '/teacher/subject/delete';
				console.log(this.deleteId);
				uni.request({
					url: getApp().globalData.URL + PATH,
					header: {
						'token': uni.getStorageSync('token'),
					},
					method: 'POST',
					data: {
						id: this.deleteId,
					},
					success: (res) => {
						console.log(res);
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						this.notify('success', '数据已删除');
						this.getAllSubject();
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})
			}
		}
	}
</script>

<style lang="less" scoped>
	.buttons {
		display: flex;
		flex-direction: row;
		height: 30px;

	}
</style>
<template>
	<view class="container">
		<u-notify ref="uNotify"></u-notify>
		<uni-popup ref="alertDialog" type="dialog">
			<uni-popup-dialog type="error" cancelText="取消" confirmText="删除" title="教师删除" content="确定删除吗？"
				@confirm="subDelete()"></uni-popup-dialog>
		</uni-popup>
		<view class="list" v-for="(item, departId) in departList" :key="departId">
			<uni-section :title="item" type="line" padding>
				<view style="color:blueviolet; font-size: 20px;" v-if="!findByDepartId(departId).length" class="">
					暂无负责老师
				</view>
				<view class="" v-for="(item, index) in findByDepartId(departId)" :key="index">
					<view class="input-box">
						<uni-easyinput v-model="item.id" :styles="styles" :placeholderStyle="placeholderStyle"
							placeholder="教师工号" @input="input"></uni-easyinput>
						<uni-easyinput v-model="item.name" :styles="styles" :placeholderStyle="placeholderStyle"
							placeholder="教师姓名" @input="input"></uni-easyinput>
					</view>
					<u-gap height="10"></u-gap>
					<view class="buttons">
						<button size="mini" type="warn" @click="clickDelete(item)">删除</button>
						<button size="mini" type="primary" @click="submit(item)">提交</button>
					</view>
					<u-gap height="10"></u-gap>
				</view>
				<u-gap height="20"></u-gap>
				<button @click="addTeacher(departId)" size=" default" type="primary">添加老师</button>
			</uni-section>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 学院列表，顺序需与后端一致
				departList: [
					'管理员', '文法学院', '商务信息学院', '商务经济学院', '商务外语学院', '艺术设计学院', '酒店管理学院', '工商管理学院'
				],
				// 教师列表
				teacherList: [],
				// 删除老师的id
				deleteId: 0,
				// 需要添加的老师信息
				teacher: {
					id: 0,
					name: '',
					departId: 1,
				}
			}
		},
		onLoad() {
			this.getAllTeacher();
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
			// 添加老师
			submit(teacher) {
				const path = '/teacher/update';
				uni.request({
					url: getApp().globalData.URL + path,
					header: {
						'token': uni.getStorageSync('token'),
					},
					method: 'POST',
					data: teacher,
					success: (res) => {
						console.log(res);
						if (res.data.code === 0) {
							this.notify('error', res.data.msg);
							return;
						}
						this.notify('success', '已完成数据变更');
						this.getAllTeacher();
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})
			},
			// 添加老师
			addTeacher(departId) {
				this.teacherList.push({
					name: '',
					departId
				})
			},
			// 通过departId找到对应学院的老师,return 数组
			findByDepartId(departId) {
				console.log(departId);
				return this.teacherList.filter((item) => item.departId === departId)
			},
			// 获取所有老师
			getAllTeacher() {
				const path = '/teacher';
				uni.request({
					url: getApp().globalData.URL + path,
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
						this.teacherList = res.data.data;
					},
					fail: (error) => {
						console.log(error);
						this.notify('error', '网络异常！');
					}
				})
			},
			// 老师删除
			subDelete() {
				const path = '/teacher/delete';
				console.log(this.deleteId);
				uni.request({
					url: getApp().globalData.URL + path,
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
						this.getAllTeacher();
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

	.input-box {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
</style>
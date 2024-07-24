<template>
	<view class="">
		<view class="background">
		</view>
		<!-- 选择文件上传方式 -->
		<u-action-sheet :show="fileChoice" :actions="selectList" title="文件在哪？" description="文件在哪里呀文件在哪里" cancelText="取消"
			:safeAreaInsetBottom="true" @select="handleSelect($event)" @close="fileChoice = false">
		</u-action-sheet>
		<!-- 确认上传按钮 -->
		<u-modal :content="modalContent" :show="modalShow" showCancelButton closeOnClickOverlay
			@confirm="handleClickButton()" @cancel="modalShow = false" @close="modalShow = false"></u-modal>
		<!-- 上传时的加载页面 -->
		<u-loading-page bgColor="rgba(0, 0, 0, 0.3)" fontSize="25" iconSize="40" loadingColor="#ddd" color="#eee"
			:loadingText="loadingText" loading-mode="spinner" :loading="loading">
		</u-loading-page>
		<!-- 上传成功或失败提示消息 -->
		<u-notify ref="uNotify"></u-notify>
		<view class="content">
			<u-gap height="20"></u-gap>
			<!-- 项目主题 -->
			<u-gap height="10"></u-gap>
			<!-- 项目名称 -->

			<view class="project-name">
				<picker mode="selector" :range="subjects" range-key="name" @change="subjectSelect($event)"
					style="border-bottom: 1px solid rgba(0, 0, 0,0.2);">
					<view style="line-height: 40px;padding-left: 5px;">项目主题： {{ subject.name }}</view>
				</picker>

				<u-input border="none" v-model="project.project.title" type="text">
					<u-text size="16" align="center" line-height="40px" margin="0 0 0 5px" text="项目名称 :"
						slot="prefix"></u-text>
				</u-input>
			</view>
			<u-gap height="8"></u-gap>
			<!-- 负责人信息 -->
			<view class="leader">
				<!-- 负责人姓名 -->
				<view class="name">
					<u-input :disabled="true" :value="leader.leaderName" border="bottom" type="text">
						<u-text size="15" align="center" line-height="30px" margin="0 0 0 0" text="负责人 :"
							slot="prefix"></u-text>
					</u-input>
				</view>
				<!-- 负责人学号 -->
				<view class="id">
					<u-input :disabled="true" :value="leader.leaderId" border="bottom" type="number">
						<u-text size="15" align="center" line-height="30px" margin="0 0 0 0" text="学号 :"
							slot="prefix"></u-text>
					</u-input>
				</view>
				<!-- 负责人学院 -->
				<view class="depart">
					<u-input :disabled="true" :value="leader.leaderDepart" border="bottom" type="textd">
						<u-text size="15" align="center" line-height="30px" margin="0 0 0 0" text="学院 :"
							slot="prefix"></u-text>
					</u-input>
				</view>
				<!-- 负责人电话 -->
				<view class="phone">
					<u-input border="bottom" maxlength="11" v-model="project.project.leaderPhone" type="tel">
						<u-text size="15" align="center" line-height="30px" margin="0 0 0 0" text="联系电话 :"
							slot="prefix"></u-text>
					</u-input>
				</view>
			</view>
			<u-gap height="8"></u-gap>
			<!-- 成员信息 -->
			<view class="teammate">
				<!-- 单个成员 -->
				<view v-for="(item, index) in peopleNumber" :key="index">
					<view class="person">
						<!-- 队员姓名 -->
						<view class="name">
							<u-input placeholder="姓名" v-model="project.members[index].memberName" border="none"
								type="text">
								<u-text size="14" align="center" line-height="30px" margin="0 0 0 0"
									:text="`组员 ${index + 1}:`" slot="prefix"></u-text>
							</u-input>
						</view>
						<!-- 队员学号 -->
						<u-row class="id_phone">
							<u-col span="5" class="id">
								<u-input maxlength="11" v-model="project.members[index].memberId" border="none"
									type="number">
									<u-text size="14" align="center" line-height="30px" margin="0 0 0 0" text="学号 :"
										slot="prefix"></u-text>
								</u-input>
							</u-col>
							<!-- 垂直分割线 -->
							<u-col customStyle="height: 20px" span="2">
								<u-line direction="col" color="black"></u-line>
							</u-col>
							<!-- 队员电话 -->
							<u-col span="5" class="phone">
								<u-input border="none" maxlength="11" v-model="project.members[index].memberPhone"
									type="tel">
									<u-text size="14" align="center" line-height="30px" margin="0 0 0 0" text="电话 :"
										slot="prefix"></u-text>
								</u-input>
							</u-col>
						</u-row>
					</view>
					<u-gap height="20"></u-gap>
				</view>

				<!-- 增加成员按钮 -->
				<view class="add-button-view" v-if="peopleNumber < 4">
					<image class="add-button" @click="handleAddPeople()" src="@/static/icon/add-button.png"
						mode="widthFix">
					</image>
				</view>
			</view>

			<u-gap height="10"></u-gap>
			<view class="upload-text">
				{{ uploadText }}
			</view>
			<u-gap height="10"></u-gap>
			<!-- 文件上传 -->
			<view class="upload" @click="fileChoice = true">
				<view class="box">
					<image mode="widthFix" class="upload-img" src="@/static/uploads/upload.png" alt="点击上传"></image>
					<view class="upload-title">
						点击上传
					</view>
				</view>
				<u-gap height="20"></u-gap>
			</view>
			<u-gap height="100"></u-gap>
			<!-- bottom -->

		</view>
		<button @click="handleClickButton()" class="submit-button"> 提 交 </button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 可选主题列表
				subjects: [
					/* {
											id: 1,
											name: '知行杯',
										},
										{
											id: 2,
											name: '青春绽放',
										} */
				],
				// 已经选择的主题
				subject: {},
				// 文件上传的两个选项
				selectList: [{
						id: 1,
						name: '微信聊天文件上传',
						color: 'black',
						fontSize: '20',
					},
					{
						id: 2,
						name: '文件本地上传',
						color: 'black',
						fontSize: '20',
					},
					{
						id: 3,
						name: '百度网盘文件上传(未开发)',
						color: 'black',
						fontSize: '20',
						disabled: true,
					}
				],
				// 负责人默认信息，仅电话可编辑
				leader: {}, // leaderId，leaderName，leaderDepart
				// 项目信息
				project: {
					members: [{
						memberId: '',
						memberName: '',
						memberPhone: ''
					}], // memberId,memberName,memberPhone
					project: {
						// 选择的主题
						subjectId: '',
						leaderPhone: '',
						title: '',
					},

				},
				peopleNumber: 1, // 队员人数
				file: '', // 文件路径
				fileChoice: false, // 文件选择菜单
				addButtonPath: '', // 添加按钮的贴图
				uploadText: '！请上传《社会实践团队项目申报书》《社会实践信息统计表》《社会实践外出情况个人汇总报》《社会实践安全注意事项及安全承诺书》打包成压缩文件上传', // 上传提示
				modalContent: '确认上传吗，上传后不可修改哟',
				modalShow: false,
				loadingText: '上传中,请勿退出', // 上传时文字
				loading: false, //是否加载中
			}
		},
		onLoad() {
			// 判断项目是否已存在，如果存在立即跳转至finish
			this.judgeExist();
			const path1 = '/leader';
			// 获取负责人(本人)信息
			uni.request({
				url: getApp().globalData.URL + path1,
				method: 'GET',
				header: {
					'token': uni.getStorageSync('token'),
				},
				success: (res) => {
					console.log(res);
					this.leader = res.data.data;
				},
				fail: (error) => {
					console.log(error);
				}
			})
			// 获取主题列表
			const path2 = '/subject'
			uni.request({
				url: getApp().globalData.URL + path2,
				method: 'GET',
				header: {
					'token': uni.getStorageSync('token'),
				},
				success: (res) => {
					console.log(res);
					this.subjects = res.data.data;
				},
				fail: (error) => {
					console.log(error);
				}
			})
		},
		methods: {
			// 判断项目是否已存在，如果存在立即跳转至finish
			judgeExist() {
				const path = '/project/judge';
				uni.request({
					url: getApp().globalData.URL + path,
					method: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						console.log(res);
						if (res.data.data === 1) {
							uni.redirectTo({
								url: '/pages/finish/finish',
							})
						}
					}
				})

			},
			// 主题选择时触发
			subjectSelect(e) {
				console.log(e.detail.value);
				console.log(this.subjects);
				const subject = this.subjects[e.detail.value];
				console.log(subject);
				this.subject = subject;
				this.project.project.subjectId = subject.id;
			},
			// 点击添加成员按钮触发
			handleAddPeople() {
				this.peopleNumber += 1;
				// 向成员列表中添加对象
				const newMember = {
					memberId: '',
					memberName: '',
					memberPhone: ''
				};
				this.project.members.push(newMember);
			},
			// 点击了选项触发
			handleSelect(obj) {
				console.log(obj);
				// obj 传递被选择了的对象
				// id = 1,聊天文件上传 2，百度网盘上传
				switch (obj.id) {
					case 1:
						wx.chooseMessageFile({
							count: 1,
							type: 'file',
							extension: ['.zip', '.rar', '.7z', '.tar', 'gz', '.tgz'],
							success: (res) => {
								console.log(res);
								this.file = res.tempFiles[0].path;
							},
							fail: (error) => {
								console.log(error);
							}
						})
						break;
					case 2:
						uni.chooseFile({
							count: 1,
							type: 'all',
							extension: ['.zip', '.rar', '.7z', '.tar', 'gz', '.tgz'],
							success: (res) => {
								console.log(res);
								this.file = res.tempFiles[0].path;
							},
							fail: (error) => {
								console.log(error);
							}
						})
						break;
					case 3:
						break;
				}
			},
			// 点击上传按钮，判断值是否有效
			handleClickButton() {
				// 判断各字段是否存在
				if (this.project.project.subjectId === '') {
					console.log("字段请填写完整");
					this.notify('error', '字段请填写完整');
					return;
				}

				// 项目标题
				if (this.project.project.title === '') {
					console.log("字段请填写完整");
					this.notify('error', '字段请填写完整');
					return;
				}

				// 负责人手机号
				if (!uni.$u.test.mobile(this.project.project.leaderPhone)) {
					console.log("负责人手机号错误");
					this.notify('error', '负责人手机号错误');
					return;
				}

				// 遍历成员
				for (let i = 0; i < this.peopleNumber; i++) {
					if ((this.project.members[i].memberId === '') || (this.project.members[i].memberName === '') ||
						(!uni.$u.test.mobile(this.project.members[i].memberPhone))) {
						console.log(i);
						console.log(this.peopleNumber);
						console.log(this.project.members[i].memberId);
						console.log(this.project.members[i].memberPhone);
						console.log(this.project.members[i].memberName);
						console.log("成员信息错误");
						this.notify('error', '成员信息错误');
						return;
					}
				}
				// 文件是否存在
				if (this.file === '') {
					console.log("请上传文件");
					this.notify('error', '请上传文件');
					return;
				}
				// 检验通过后上传数据 
				if (this.modalShow) {
					this.uploadData();
				} else {
					this.modalShow = true;
				}
			},
			// 上传数据
			uploadData() {
				const PATH = '/project/upload';
				// const formData = new FormData();

				// formData.append('project', JSON.stringify(this.project.project));
				// formData.append('members', JSON.stringify(this.project.members));

				uni.uploadFile({
					url: getApp().globalData.URL + PATH,
					filePath: this.file,
					name: 'file',
					header: {
						'token': uni.getStorageSync('token')
					},
					formData: {
						'project': JSON.stringify(this.project.project),
						'members': JSON.stringify(this.project.members)
					},
					success: (res) => {
						this.loading = false;

						if (res.data.code === 0) {
							// 报错,返回报错信息
							this.notify('error', res.data.msg);
						}
						// 跳转到~~~~~~~~~~~
						const leader = this.leader.leaderName;
						const title = this.project.project.title;
						const members = this.project.members;
						uni.redirectTo({
							url: '/pages/finish/finish',
						});
						console.log(res);
					},
					fail: (error) => {
						this.loading = false;
						// 弹弹框
						this.notify('error', '网络异常,请稍后重试');
						console.log(error);
					}
				})
				this.modalShow = false;
				this.loading = true;
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
	.background {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: -1;
		background-image: linear-gradient(to bottom, #ffe0dd, #fdecdc, #fcf9da);
	}

	.content {
		width: 90%;
		margin: auto;
	}

	.project-name {
		// height: 40px;
	}

	.leader {
		padding-bottom: 20px;
		overflow: hidden;
	}

	.project-name,
	.leader,
	.teammate,
	.upload,
	{
	background-color: white;
	border-radius: 10px;
	}


	// 队员
	.teammate {
		min-height: 200px;
		padding-top: 20px;

		.person {
			width: 90%;
			margin: auto;
			border-bottom: 1px solid black;
		}

		.id_phone {
			display: flex;
			flex-direction: row;

		}

		// 添加按钮
		.add-button-view {
			width: 90%;
			margin: auto;
		}

		.add-button {
			border-radius: 50%;
			width: 30px;
		}
	}

	// upload

	.upload-text {
		font-family: '';
	}

	.upload {
		.box {
			width: fit-content;
			margin: auto;
			display: flex;
			flex-direction: column;
			text-align: center;
			justify-content: center;
		}

		.upload-title {
			color: grey;
			font-size: 20px;
			letter-spacing: 1.5px;
		}
	}

	.upload-img {
		width: 120px;
	}

	// 提交按钮

	.submit-button {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		height: 45px;
		z-index: 100;
		background-color: #df805b;
		color: white;
	}
</style>
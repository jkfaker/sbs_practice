<template>
	<view class="container">
		<view class="background"></view>
		<view class="bgc"></view>
		<view class="content">
			<u-gap height="50"></u-gap>
			<view class="success">
				<view class="icon">
					<uni-icons type="checkbox" color="white" size="36"></uni-icons>
				</view>
				<view class="title">
					提交成功，待老师审核！
				</view>
			</view>
			<u-gap height="20"></u-gap>
			<!-- 项目信息 -->
			<view class="box">
				<view class="projects">
					<u-gap height="20"></u-gap>
					<view class="subject">
						{{ project.subjectName }} -- 2024暑期社会实践
					</view>
					<u-gap height="15"></u-gap>
					<view class="title">
						{{ project.projectName }}
					</view>
					<u-gap height="12"></u-gap>
					<view class="time">
						<view class="icon">
							<uni-icons type="calendar-filled" color="orange" size="24"></uni-icons>
						</view>
						<view class="text">
							2024/06/24-2024/07/15
						</view>
					</view>
					<u-gap height="8"></u-gap>
					<view class="address">
						<view class="icon">
							<uni-icons type="location" size="24"></uni-icons>
						</view>
						<view class="text">
							答辩时间与地点另行通知
						</view>
					</view>
					<u-gap height="16"></u-gap>
				</view>
			</view>
			<u-gap height="20"></u-gap>
			<!-- 成员信息 -->
			<view class="members-box">
				<view class="members">
					<u-gap height="14"></u-gap>
					<view class="title">
						参与者
					</view>
					<u-gap height="8"></u-gap>
					<view class="people">
						<view class="person" v-for="(item, index) in members" :key="index">
							<view class="icon">
								<uni-icons type="person-filled" color="white" size="50"></uni-icons>
							</view>
							<u-gap height="5"></u-gap>
							<view class="name">
								{{ item.memberName }}
							</view>
						</view>
					</view>
					<u-gap height="10"></u-gap>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				members: [
					// {
					// 	id: 10,
					// 	memberName: '孙二娘',
					// },
					// {
					// 	id: 2,
					// 	memberName: '顾大嫂',
					// },
					// {
					// 	id: 3,
					// 	memberName: '李大娘',
					// },
					// {
					// 	id: 4,
					// 	memberName: '潘金莲',
					// },
					// {
					// 	id: 5,
					// 	memberName: '武大郎',
					// }
				],
				project: {}
			}
		},
		onLoad() {
			this.getMembers();
			this.getProjectInfo();
		},
		methods: {

			// 从后端获取成员列表
			getMembers() {
				const path = '/member';
				uni.request({
					url: getApp().globalData.URL + path,
					methods: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						console.log(res);
						this.members = res.data.data;
					}
				})
			},
			// 从后端获取项目主题和项目名称
			getProjectInfo() {
				const path = '/project/name';
				uni.request({
					url: getApp().globalData.URL + path,
					methods: 'GET',
					header: {
						'token': uni.getStorageSync('token'),
					},
					success: (res) => {
						console.log(res);
						this.project = res.data.data;
					}

				})
			},
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
		z-index: -10;
		background-color: #f3f2f2;
	}

	.bgc {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 60%;
		z-index: -1;
		background-image: linear-gradient(to bottom, #f39c7a, white);
	}


	.content {
		width: 90%;
		margin: auto;
	}

	.success {
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: center;

		.icon {
			margin-right: 3px;
		}

		.title {
			color: white;
			font-size: 20px;
			font-weight: 400;
		}
	}

	.box {
		background-color: white;
		border-radius: 5px;

		.projects {
			width: 90%;
			margin: auto;

			.subject {
				font-weight: bold;
				font-size: 18px;
			}

			.title {
				font-weight: bold;
				font-size: 16px;
			}

			.time {
				display: flex;
				flex-direction: row;
				align-items: center;
				justify-content: start;

				.icon {
					margin-right: 7px;
				}
			}


			.address {
				display: flex;
				flex-direction: row;
				align-items: start;
				justify-content: start;

				.icon {
					margin-right: 7px;
				}
			}
		}


	}

	.members-box {
		background-color: white;
		border-radius: 5px;
		box-shadow: 1px 3px rgba(0, 0, 0, 0.2);
		border: 1px solid rgba(0, 0, 0, 0.2);

		.members {
			width: 90%;
			margin: auto;

			.title {
				font-weight: bold;
				font-size: 16px;
			}

			.people {
				display: flex;
				flex-direction: row;
				justify-content: start;
				align-items: center;

				.person {
					margin-right: 14px;

					.icon {
						border-radius: 50%;
						background-color: lightgray;
					}

					.name {
						color: gray;
						font-size: 12px;
						text-align: center;
					}
				}
			}
		}
	}
</style>
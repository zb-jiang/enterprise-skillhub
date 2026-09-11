## 本地代码和官方upstream同步机制
- 本地main分支用来定期同步upstream官方更新
- 本地dev分支用来做本地的定制化开发
- main分支会定期和dev合并
- 合并后的最全代码推送到origin的dev分支

## 定期同步命令
1. 官方更新同步到 main（--ff-only 保证 main 始终是官方代码的干净镜像）
git switch main
git fetch upstream
git merge --ff-only upstream/main

2. 合并到 dev（如有冲突需手动解决）
git switch dev
git merge main

3. 推送合并结果到 origin
git push origin dev


## 本地代码仓库在upstream官方代码基础上的定制
- 上游设计：`public` / `namespace-only` 可见性的 skill 发布**必须**先过安全扫描（`SkillPublishService.requiresSecurityScanner`，无配置可绕过）。本地代码已做两处放宽（`application-local.yml`）：扫描器默认关闭（`enabled` 默认 `false`），并新增 `skillhub.security.scanner.required-for-publish=false` 豁免发布强扫——这是本地代码相对上游的核心定制点之一，同步上游时留意。
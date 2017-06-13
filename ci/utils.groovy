
def upload2softrepo(pkg, dir) {
     def config = readYaml file: 'ci/config.yml'
     def repo_host = config.softrepo.ssh_host
     def repo_dir = config.softrepo.dir
     def dst_dir = repo_dir + dir
     def port = config.softrepo.ssh_port
     sh "ssh -t -p ${port} ${repo_host} \"ls ${dst_dir} || mkdir ${dst_dir}\""
     sh "cd .. && scp -P${port} ${pkg} ${repo_host}:${dst_dir}"
}


def ssh_cmd(host, port, cmd) {
    return {
        node {
            sh "ssh -t -p ${port} ${host} \"${cmd}\""
        }
    }
}

def deploy_cmd(host, port, dir, pkg) {
    return {
        node {
            def dir_name = pkg - ".tar.gz"
            def dst_dir="/opt"
            sh "scp -P${port} $dir/${pkg} ${host}:${dst_dir}"
            def untar_cmd = "(cd ${dst_dir}; tar xvf ${pkg})"
            def cp_cmd = "(ls /usr/local/jenkins_ci_demo||mkdir /usr/local/jenkins_ci_demo;cp -r /opt/${dir_name}/* /usr/local/jenkins_ci_demo/)"
            def install_cmd = "(cd /usr/local/jenkins_ci_demo;python setup.py install)"
            def cmd = "(${untar_cmd}; ${cp_cmd}; ${install_cmd})"
            sh "ssh -t -p ${port} ${host} \"${cmd}\""
        }
    }
}

def deploy2dev(dir, pkg) {
    def config = readYaml file: 'ci/config.yml'
    def steps4parallel = [:]
    for( i = 0; i < config.dev.dockers.size(); i++) {
        def docker_cfg = config.dev.dockers[i]
        def name = docker_cfg.name
        def step_name = "echoing ${name}"
        steps4parallel[step_name] = deploy_cmd(docker_cfg.ssh_host, docker_cfg.ssh_port, dir, pkg)
    }
    parallel steps4parallel
}

def deploy2qa_xm(version, pkg) {
    def config = readYaml file: 'ci/config.yml'
    def repo_url = config.softrepo.url
    def dl_url = repo_url + version + '/' + pkg
    def dst_dir = '/opt/' + pkg
    def dl_cmd = 'wget --no-check-certificate ' + dl_url + ' -O ' + dst_dir
    //todo: using softlink
    def install_dir = config.lbdns.install_dir
    def install_cmd = "(mkdir ${install_dir};tar xvf ${dst_dir} -C ${install_dir})"
    def cmd = "(${dl_cmd}; ${install_cmd})"
    def steps4parallel = [:] 

    for( i = 0; i < config.deploy.qa_xm.lbdns_dockers.size(); i++) {
        def docker_cfg = config.deploy.qa_xm.lbdns_dockers[i]
        def name = docker_cfg.name
        def step_name = "echoing ${name}"
        steps4parallel[step_name] = ssh_cmd(docker_cfg.ssh_host, docker_cfg.ssh_port, cmd)
    }
    parallel steps4parallel
}

return this; 

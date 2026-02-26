document.addEventListener('DOMContentLoaded', () => {

    // === Navegação das Tabs ===
    const navBtns = document.querySelectorAll('.nav-btn');
    const sections = document.querySelectorAll('.content-section');

    navBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            navBtns.forEach(b => b.classList.remove('active'));
            sections.forEach(s => s.classList.remove('active'));

            btn.classList.add('active');
            document.getElementById(btn.dataset.target).classList.add('active');
        });
    });

    // === Lógica de Upload de Arquivo ===
    const dropArea = document.getElementById('dropArea');
    const fileInput = document.getElementById('pdfFile');
    const fileInfo = document.getElementById('fileInfo');
    const fileNameDisplay = document.getElementById('fileName');
    const removeFileBtn = document.getElementById('removeFile');
    const submitBtn = document.getElementById('submitBtn');
    let currentFile = null;

    // Clique na área de drop aciona o input escondido
    dropArea.addEventListener('click', () => fileInput.click());

    // Efeitos de Drag and Drop
    dropArea.addEventListener('dragover', (e) => {
        e.preventDefault();
        dropArea.classList.add('dragover');
    });

    dropArea.addEventListener('dragleave', () => dropArea.classList.remove('dragover'));

    dropArea.addEventListener('drop', (e) => {
        e.preventDefault();
        dropArea.classList.remove('dragover');
        if (e.dataTransfer.files.length > 0) {
            handleFileSelection(e.dataTransfer.files[0]);
        }
    });

    // Seleção manual de arquivo
    fileInput.addEventListener('change', (e) => {
        if (e.target.files.length > 0) {
            handleFileSelection(e.target.files[0]);
        }
    });

    // Função para validar e exibir arquivo PDF
    function handleFileSelection(file) {
        if (file.type !== 'application/pdf') {
            showToast('Por favor, selecione apenas arquivos PDF.');
            return;
        }

        currentFile = file;
        fileNameDisplay.textContent = file.name;
        dropArea.classList.add('hidden');
        fileInfo.classList.remove('hidden');
        submitBtn.disabled = false;
    }

    // Remover arquivo selecionado
    removeFileBtn.addEventListener('click', () => {
        currentFile = null;
        fileInput.value = '';
        dropArea.classList.remove('hidden');
        fileInfo.classList.add('hidden');
        submitBtn.disabled = true;
    });

    // === Requisição da API ===
    const uploadForm = document.getElementById('uploadForm');
    const loader = document.getElementById('loader');
    const resultPanel = document.getElementById('resultPanel');

    // Chave de API da aplicação embutida para facilitar testes públicos no Portfólio
    const APP_API_KEY = 'A5X9_Secret_Key_Para_O_Portfoli0!';

    uploadForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        if (!currentFile) return;

        // Mostrar Loader
        uploadForm.classList.add('hidden');
        resultPanel.classList.add('hidden');
        loader.classList.remove('hidden');

        try {
            const formData = new FormData();
            formData.append('file', currentFile);

            const response = await fetch('/api/curriculos/analisar', {
                method: 'POST',
                headers: {
                    'x-api-key': APP_API_KEY
                },
                body: formData
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Erro ao processar currículo');
            }

            const data = await response.json();
            renderResult(data);

        } catch (error) {
            showToast(error.message);
            uploadForm.classList.remove('hidden');
        } finally {
            loader.classList.add('hidden');
        }
    });

    // Preencher a UI com os dados do DTO retornado do Spring Boot
    function renderResult(dto) {
        document.getElementById('resName').textContent = dto.nome || 'Não identificado';
        document.getElementById('resEmail').textContent = dto.email || 'Não encontrado';
        document.getElementById('resPhone').textContent = dto.telefone || 'Não encontrado';
        document.getElementById('resLevel').textContent = dto.nivelEstimado || 'Desconhecido';
        document.getElementById('resSummary').textContent = dto.resumoExperiencia || 'Sem resumo disponível.';

        // Lógica de Skills (Criar as tags visualmente)
        const skillsContainer = document.getElementById('resSkills');
        skillsContainer.innerHTML = ''; // Limpar tags antigas
        if (dto.listaDeHabilidades && dto.listaDeHabilidades.length > 0) {
            dto.listaDeHabilidades.forEach(skill => {
                const span = document.createElement('span');
                span.className = 'skill-tag';
                span.textContent = skill;
                skillsContainer.appendChild(span);
            });
        } else {
            skillsContainer.textContent = 'Nenhuma habilidade explícita encontrada.';
        }

        // Links sociais (Exibidos condicionalmente)
        const linkIn = document.getElementById('resLinkedin');
        const linkGh = document.getElementById('resGithub');

        if (dto.linkLinkedin) {
            linkIn.href = dto.linkLinkedin;
            linkIn.classList.remove('hidden');
        } else {
            linkIn.classList.add('hidden');
        }

        if (dto.linkGithub) {
            linkGh.href = dto.linkGithub;
            linkGh.classList.remove('hidden');
        } else {
            linkGh.classList.add('hidden');
        }

        // Mostrar o painel final com Fade-in
        uploadForm.classList.remove('hidden');
        resultPanel.classList.remove('hidden');

        // Reset state do form
        removeFileBtn.click();
    }

    // Função utilitária de alertas (Toast notification)
    const toast = document.getElementById('toast');
    let toastTimeout;
    function showToast(message) {
        toast.textContent = message;
        toast.classList.remove('hidden');
        clearTimeout(toastTimeout);
        toastTimeout = setTimeout(() => {
            toast.classList.add('hidden');
        }, 4000);
    }

    // === Lógica de Interface para o Match com Vaga ===
    const matchDropArea = document.getElementById('matchDropArea');
    const matchFileInput = document.getElementById('matchPdfFile');
    const matchFileInfo = document.getElementById('matchFileInfo');
    const matchFileNameDisplay = document.getElementById('matchFileName');
    const matchRemoveFileBtn = document.getElementById('matchRemoveFile');
    const matchSubmitBtn = document.getElementById('matchSubmitBtn');
    let currentMatchFile = null;

    matchDropArea.addEventListener('click', () => matchFileInput.click());
    matchDropArea.addEventListener('dragover', (e) => { e.preventDefault(); matchDropArea.classList.add('dragover'); });
    matchDropArea.addEventListener('dragleave', () => matchDropArea.classList.remove('dragover'));
    matchDropArea.addEventListener('drop', (e) => {
        e.preventDefault();
        matchDropArea.classList.remove('dragover');
        if (e.dataTransfer.files.length > 0) handleMatchFileSelection(e.dataTransfer.files[0]);
    });
    matchFileInput.addEventListener('change', (e) => {
        if (e.target.files.length > 0) handleMatchFileSelection(e.target.files[0]);
    });

    function handleMatchFileSelection(file) {
        if (file.type !== 'application/pdf') {
            showToast('Por favor, selecione apenas arquivos PDF.');
            return;
        }
        currentMatchFile = file;
        matchFileNameDisplay.textContent = file.name;
        matchDropArea.classList.add('hidden');
        matchFileInfo.classList.remove('hidden');
        matchSubmitBtn.disabled = false;
    }

    matchRemoveFileBtn.addEventListener('click', () => {
        currentMatchFile = null;
        matchFileInput.value = '';
        matchDropArea.classList.remove('hidden');
        matchFileInfo.classList.add('hidden');
        matchSubmitBtn.disabled = true;
    });

    // === Requisição API - Match ===
    const matchForm = document.getElementById('matchForm');
    const matchLoader = document.getElementById('matchLoader');
    const matchResultPanel = document.getElementById('matchResultPanel');
    const vagaDescricao = document.getElementById('vagaDescricao');

    matchForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        if (!currentMatchFile || !vagaDescricao.value.trim()) {
            showToast("Anexe um currículo e descreva a vaga primeiro.");
            return;
        }

        matchForm.classList.add('hidden');
        matchResultPanel.classList.add('hidden');
        matchLoader.classList.remove('hidden');

        try {
            const formData = new FormData();
            formData.append('file', currentMatchFile);
            formData.append('vaga', vagaDescricao.value.trim());

            const response = await fetch('/api/curriculos/match', {
                method: 'POST',
                headers: {
                    'x-api-key': APP_API_KEY
                },
                body: formData
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Erro ao processar o match');
            }

            const data = await response.json();

            // Render Result
            document.getElementById('matchScoreBadge').textContent = `${data.notaCompatibilidade}% Match`;

            // Colorindo o badge de acordo com a nota
            let badgeBg = 'rgba(239, 68, 68, 0.15)'; // Red
            let badgeColor = 'var(--danger)';
            if (data.notaCompatibilidade >= 50 && data.notaCompatibilidade < 75) {
                badgeBg = 'rgba(245, 158, 11, 0.15)'; // Yellow
                badgeColor = '#f59e0b';
            } else if (data.notaCompatibilidade >= 75) {
                badgeBg = 'rgba(16, 185, 129, 0.15)'; // Green
                badgeColor = 'var(--success)';
            }
            const matchScoreBadge = document.getElementById('matchScoreBadge');
            matchScoreBadge.style.background = badgeBg;
            matchScoreBadge.style.color = badgeColor;
            matchScoreBadge.style.borderColor = badgeColor;

            document.getElementById('matchJustificativa').textContent = data.justificativa || 'Nenhuma justificativa providenciada pela IA.';

            matchForm.classList.remove('hidden');
            matchResultPanel.classList.remove('hidden');
            matchRemoveFileBtn.click(); // reseta o form

        } catch (error) {
            showToast(error.message);
            matchForm.classList.remove('hidden');
        } finally {
            matchLoader.classList.add('hidden');
        }
    });
});

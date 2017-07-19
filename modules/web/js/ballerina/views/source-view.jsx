/**
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import React from 'react';
import PropTypes from 'prop-types';
import CSSTransitionGroup from 'react-transition-group/CSSTransitionGroup';
import { Overlay, Popover } from 'react-bootstrap/lib';
import classNames from 'classnames';
import commandManager from 'command';
import File from './../../workspace/file';
import { DESIGN_VIEW } from './constants';
import SourceEditor from './source-editor';

/**
 * Source View Component
 */
class SourceView extends React.Component {

    /**
     * Constructor
     * @param {*} props React props
     */
    constructor(props) {
        super(props);
        this.state = {
            displayErrorList: props.displayErrorList,
            syntaxErrors: [],
        };
        this.errorListPopoverTarget = undefined;
        this.onSourceEditorLintErrors = this.onSourceEditorLintErrors.bind(this);
        this.toggleErrorListPopover = this.toggleErrorListPopover.bind(this);
    }

    /**
     * Update state with new props
     * @param {*} newProps The new props object.
     */
    componentWillReceiveProps(newProps) {
        this.setState({
            displayErrorList: newProps.displayErrorList,
        });
    }

    /**
     * When source editor finds errors
     * @param {array} lintErrors List of errors received by validations
     */
    onSourceEditorLintErrors(lintErrors) {
        this.setState({
            syntaxErrors: lintErrors,
        });
    }

    /**
     * Toggle error list popover
     */
    toggleErrorListPopover() {
        this.setState({
            displayErrorList: !this.state.displayErrorList,
        });
    }

    /**
     * Render the component
     * @returns {Component} return the component
     */
    render() {
        const hasSyntaxErrors = this.state.syntaxErrors.length > 0;

        const errorListPopover = (
            <Popover
                id="popover-basic"
                title="found syntax errors"
            >
                {
                    this.state.syntaxErrors.map((error) => {
                        return (
                            <div key={error.row + error.column}>{error.text}</div>
                        );
                    })
                }
            </Popover>
        );

        return (
            <div
                className="source-view-container"
                onClick={
                    () => {
                        if (this.state.displayErrorList) {
                            this.toggleErrorListPopover();
                        }
                    }
                }
            >
                <div className="wrapperDiv">
                    <div className="outerSourceDiv">
                        <SourceEditor
                            commandManager={this.props.commandManager}
                            file={this.props.file}
                            parseFailed={this.props.parseFailed}
                            onLintErrors={this.onSourceEditorLintErrors}
                        />
                        <div
                            className={classNames('bottom-right-controls-container',
                                            { disabled: hasSyntaxErrors })}
                        >
                            <div className={classNames('view-design-btn btn-icon',
                                        { target: this.state.displayErrorList })}
                            >
                                <div className="bottom-label-icon-wrapper">
                                    <i className="fw fw-design-view fw-inverse" />
                                </div>
                                <div
                                    className="bottom-view-label"
                                    ref={(ref) => {
                                        this.errorListPopoverTarget = ref;
                                    }}
                                    onClick={
                                    () => {
                                        if (!hasSyntaxErrors) {
                                            this.context.editor.setActiveView(DESIGN_VIEW);
                                        } else {
                                            this.toggleErrorListPopover();
                                        }
                                    }
                                }
                                >
                                    Design View
                                </div>
                                {hasSyntaxErrors &&
                                    <Overlay
                                        show={this.state.displayErrorList}
                                        container={this}
                                        target={this.errorListPopoverTarget}
                                        placement="top"
                                    >
                                        {errorListPopover}
                                    </Overlay>
                                }
                            </div>
                            {hasSyntaxErrors && !this.state.displayErrorList &&
                                <CSSTransitionGroup
                                    transitionName="error-count-badge"
                                    transitionEnterTimeout={300}
                                    transitionLeaveTimeout={300}
                                >
                                    <div className="syntax-errors-counter-container">
                                        <span className="badge">{this.state.syntaxErrors.length}</span>
                                    </div>
                                </CSSTransitionGroup>
                            }
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

SourceView.propTypes = {
    file: PropTypes.instanceOf(File).isRequired,
    commandManager: PropTypes.instanceOf(commandManager).isRequired,
    parseFailed: PropTypes.bool.isRequired,
    displayErrorList: PropTypes.bool.isRequired,
};

SourceView.contextTypes = {
    editor: PropTypes.instanceOf(Object).isRequired,
};

export default SourceView;
